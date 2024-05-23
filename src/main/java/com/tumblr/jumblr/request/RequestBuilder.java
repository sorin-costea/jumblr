package com.tumblr.jumblr.request;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.responses.AudioPostTypeAdapter;
import com.tumblr.jumblr.responses.JsonElementDeserializer;
import com.tumblr.jumblr.responses.PhotoTypeAdapter;
import com.tumblr.jumblr.responses.ResponseWrapper;
import com.tumblr.jumblr.responses.VideoPostTypeAdapter;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.VideoPost;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TumblrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Where requests are made from
 *
 * @author jc
 */
public class RequestBuilder {

  private Token token;
  private OAuthService service;
  private String hostname = "api.tumblr.com";
  private final String xauthEndpoint = "https://www.tumblr.com/oauth/access_token";
  private final String version = "0.0.13";
  private final JumblrClient client;
  private int timeoutSeconds;
  private RateLimits rateLimits = new RateLimits();

  public RequestBuilder(final JumblrClient client) {
    this.client = client;
  }

  public RateLimits getRateLimits() {
    return rateLimits;
  }

  public String getRedirectUrl(final String path) {
    final OAuthRequest request = constructGet(path, null);
    sign(request);
    final boolean presetVal = HttpURLConnection.getFollowRedirects();
    HttpURLConnection.setFollowRedirects(false);
    final Response response = request.send();
    HttpURLConnection.setFollowRedirects(presetVal);
    if (response.getCode() == 301 || response.getCode() == 302) {
      return response.getHeader("Location");
    }
    throw new JumblrException(response);
  }

  public ResponseWrapper postMultipart(final String path, final Map<String, ?> bodyMap) throws IOException {
    final OAuthRequest request = constructPost(path, bodyMap);
    sign(request);
    final OAuthRequest newRequest = RequestBuilder.convertToMultipart(request, bodyMap);
    return clear(newRequest.send());
  }

  public ResponseWrapper post(final String path, final Map<String, ?> bodyMap) {
    final OAuthRequest request = constructPost(path, bodyMap);
    sign(request);
    return clear(request.send());
  }

  /**
   * Posts an XAuth request. A new method is needed because the response from the server is not a standard Tumblr JSON
   * response.
   *
   * @param email
   *          the user's login email.
   * @param password
   *          the user's password.
   *
   * @return the login token.
   */
  public Token postXAuth(final String email, final String password) {
    final OAuthRequest request = constructXAuthPost(email, password);
    setToken("", ""); // Empty token is required for Scribe to execute XAuth.
    sign(request);
    return clearXAuth(request.send());
  }

  // Construct an XAuth request
  private OAuthRequest constructXAuthPost(final String email, final String password) {
    final OAuthRequest request = new OAuthRequest(Verb.POST, xauthEndpoint);
    request.addBodyParameter("x_auth_username", email);
    request.addBodyParameter("x_auth_password", password);
    request.addBodyParameter("x_auth_mode", "client_auth");
    return request;
  }

  public ResponseWrapper get(final String path, final Map<String, ?> map) {
    final OAuthRequest request = constructGet(path, map);
    sign(request);
    return clear(request.send());
  }

  public OAuthRequest constructGet(final String path, final Map<String, ?> queryParams) {
    final String url = "https://" + hostname + "/v2" + path;
    final OAuthRequest request = new OAuthRequest(Verb.GET, url);
    if (queryParams != null) {
      for (final Map.Entry<String, ?> entry : queryParams.entrySet()) {
        request.addQuerystringParameter(entry.getKey(), entry.getValue().toString());
      }
    }
    request.addHeader("User-Agent", "jumblr/" + version);
    request.setConnectTimeout(timeoutSeconds, TimeUnit.SECONDS);
    request.setReadTimeout(timeoutSeconds, TimeUnit.SECONDS);

    return request;
  }

  private OAuthRequest constructPost(final String path, final Map<String, ?> bodyMap) {
    final String url = "https://" + hostname + "/v2" + path;
    final OAuthRequest request = new OAuthRequest(Verb.POST, url);

    for (final Map.Entry<String, ?> entry : bodyMap.entrySet()) {
      final String key = entry.getKey();
      final Object value = entry.getValue();
      if (value == null || value instanceof File) {
        continue;
      }
      request.addBodyParameter(key, value.toString());
    }
    request.addHeader("User-Agent", "jumblr/" + version);
    request.setConnectTimeout(timeoutSeconds, TimeUnit.SECONDS);
    request.setReadTimeout(timeoutSeconds, TimeUnit.SECONDS);

    return request;
  }

  public void setConsumer(final String consumerKey, final String consumerSecret) {
    service = new ServiceBuilder().provider(TumblrApi.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
  }

  public void setToken(final String token, final String tokenSecret) {
    this.token = new Token(token, tokenSecret);
  }

  public void setToken(final Token token) {
    this.token = token;
  }

  public void setTimeoutSeconds(final int timeoutSeconds) {
    this.timeoutSeconds = timeoutSeconds;
  }

  /* package-visible for testing */ ResponseWrapper clear(final Response response) {
    rateLimits = new RateLimits(response.getHeaders());
    if (response.getCode() != 200 && response.getCode() != 201) {
      throw new JumblrException(response);
    }
    final String json = response.getBody();
    try {
      final Gson gson = new GsonBuilder().registerTypeAdapter(JsonElement.class, new JsonElementDeserializer())
          .registerTypeAdapter(Photo.class, new PhotoTypeAdapter())
          .registerTypeAdapter(VideoPost.class, new VideoPostTypeAdapter())
          .registerTypeAdapter(AudioPost.class, new AudioPostTypeAdapter()).create();
      final ResponseWrapper wrapper = gson.fromJson(json, ResponseWrapper.class);
      if (wrapper == null) {
        throw new JumblrException(response);
      }
      wrapper.setClient(client);
      return wrapper;
    } catch (final JsonSyntaxException ex) {
      throw new JumblrException(response);
    }
  }

  private Token parseXAuthResponse(final Response response) {
    final String responseStr = response.getBody();
    if (responseStr != null) {
      // Response is received in the format "oauth_token=value&oauth_token_secret=value".
      String extractedToken = null, extractedSecret = null;
      final String[] values = responseStr.split("&");
      for (final String value : values) {
        final String[] kvp = value.split("=");
        if (kvp != null && kvp.length == 2) {
          if ("oauth_token".equals(kvp[0])) {
            extractedToken = kvp[1];
          } else if ("oauth_token_secret".equals(kvp[0])) {
            extractedSecret = kvp[1];
          }
        }
      }
      if (extractedToken != null && extractedSecret != null) {
        return new Token(extractedToken, extractedSecret);
      }
    }
    // No good
    throw new JumblrException(response);
  }

  /* package-visible for testing */ Token clearXAuth(final Response response) {
    if (response.getCode() == 200 || response.getCode() == 201) {
      return parseXAuthResponse(response);
    }
    throw new JumblrException(response);
  }

  private void sign(final OAuthRequest request) {
    if (token != null) {
      service.signRequest(token, request);
    }
  }

  public static OAuthRequest convertToMultipart(final OAuthRequest request, final Map<String, ?> bodyMap)
      throws IOException {
    return new MultipartConverter(request, bodyMap).getRequest();
  }

  public String getHostname() {
    return hostname;
  }

  /**
   * Set hostname without protocol
   *
   * @param host
   *          such as "api.tumblr.com"
   */
  public void setHostname(final String host) {
    hostname = host;
  }

}
