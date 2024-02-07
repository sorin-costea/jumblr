package com.tumblr.jumblr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tumblr.jumblr.request.RequestBuilder;
import com.tumblr.jumblr.responses.ResponseWrapper;
import com.tumblr.jumblr.types.QuotePost;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for JumblrClient
 *
 * @author jc
 */
public class JumblrClientTest {

  JumblrClient client;
  RequestBuilder builder;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws IOException {
    builder = mock(RequestBuilder.class);
    client = new JumblrClient("ck", "cs", "@", "@");
    client.setRequestBuilder(builder);
    final ResponseWrapper rw = new MockResponseWrapper();
    when(builder.get(anyString(), anyMap())).thenReturn(rw);
    when(builder.post(anyString(), anyMap())).thenReturn(rw);
    when(builder.postMultipart(anyString(), anyMap())).thenReturn(rw);
    when(builder.getRedirectUrl(anyString())).thenReturn("url");
  }

  /**
   * User methods
   */

  @Test
  public void user() {
    client.user();
    verify(builder).get("/user/info", null);
  }

  @Test
  public void userDashboard() {
    client.userDashboard();
    verify(builder).get("/user/dashboard", null);

    final Map<String, Object> options = getRandomishOptions();
    client.userDashboard(options);
    verify(builder).get("/user/dashboard", options);
  }

  @Test
  public void userLimits() {
    client.userLimits();
    verify(builder).get("/user/limits", null);

    final Map<String, Object> options = getRandomishOptions();
    client.userLimits(options);
    verify(builder).get("/user/limits", options);
  }

  @Test
  public void userFollowing() {
    client.userFollowing();
    verify(builder).get("/user/following", null);

    final Map<String, Object> options = getRandomishOptions();
    client.userFollowing(options);
    verify(builder).get("/user/following", options);
  }

  @Test
  public void userLikes() {
    client.userLikes();
    verify(builder).get("/user/likes", null);

    final Map<String, Object> options = getRandomishOptions();
    client.userLikes(options);
    verify(builder).get("/user/likes", options);
  }

  @Test
  public void like() {
    final Long id = 42L;
    final String reblogKey = "hello";

    client.like(id, reblogKey);
    final Map<String, String> options = new HashMap<String, String>();
    options.put("id", id.toString());
    options.put("reblog_key", reblogKey);
    verify(builder).post("/user/like", options);
  }

  @Test
  public void unlike() {
    final Long id = 42L;
    final String reblogKey = "hello";

    client.unlike(id, reblogKey);
    final Map<String, String> options = new HashMap<String, String>();
    options.put("id", id.toString());
    options.put("reblog_key", reblogKey);
    verify(builder).post("/user/unlike", options);
  }

  @Test
  public void follow() {
    client.follow("hey.com");
    final Map<String, String> options = new HashMap<String, String>();
    options.put("url", "hey.com");
    verify(builder).post("/user/follow", options);
  }

  @Test
  public void unfollow() {
    client.unfollow("hey.com");
    final Map<String, String> options = new HashMap<String, String>();
    options.put("url", "hey.com");
    verify(builder).post("/user/unfollow", options);
  }

  /**
   * Blog methods
   */

  @Test
  public void userAvatar() {
    client.blogAvatar("hey.com");
    verify(builder).getRedirectUrl("/blog/hey.com/avatar");

    client.blogAvatar("hey.com", 64);
    verify(builder).getRedirectUrl("/blog/hey.com/avatar/64");
  }

  @Test
  public void blogFollowedBy() {
    client.blogFollowedBy("nobeerreviews", "beforevenice");
    verify(builder).get("/blog/nobeerreviews.tumblr.com/followed_by?query=beforevenice", null);
  }

  @Test
  public void blogInfo() {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("api_key", "ck");

    client.blogInfo("blog_name");
    verify(builder).get("/blog/blog_name.tumblr.com/info", map);

    client.blogInfo("blog_name.com");
    verify(builder).get("/blog/blog_name.com/info", map);

    map.put("fields[blogs]", "%3Fis_following_you");
    client.blogInfo("blog_name", map);
    verify(builder).get("/blog/blog_name.tumblr.com/info", map);
  }

  @Test
  public void blogFollowers() {
    client.blogFollowers("blog_name");
    verify(builder).get("/blog/blog_name.tumblr.com/followers", null);

    final Map<String, Object> options = getRandomishOptions();
    client.blogFollowers("blog_name", options);
    verify(builder).get("/blog/blog_name.tumblr.com/followers", options);
  }

  @Test
  public void blogLikes() {
    client.blogLikes("hey.com");
    verify(builder).get("/blog/hey.com/likes", getApiKeyOptions());

    final Map<String, Object> options = getRandomishOptions();
    client.blogLikes("hey.com", options);
    options.putAll(getApiKeyOptions());
    verify(builder).get("/blog/hey.com/likes", options);
  }

  @Test
  public void blogNotifications() throws IOException {
    client.blogNotifications("hey.com");
    verify(builder).get("/blog/hey.com/notifications", getApiKeyOptions());

    final Map<String, Object> options = getRandomishOptions();
    client.blogNotifications("hey.com", options);
    options.putAll(getApiKeyOptions());
    verify(builder).get("/blog/hey.com/notifications", options);
  }

  @Test
  public void blogPostNotes() throws IOException {
    final Long id = 42L;
    client.blogPostNotes("hey.com", id);
    verify(builder).get("/blog/hey.com/notes", getPostOptions());

    final Map<String, Object> options = getRandomishOptions();
    client.blogPostNotes("hey.com", id, options);
    options.putAll(getPostOptions());
    verify(builder).get("/blog/hey.com/notes", options);
  }

  @Test
  public void blogPosts() {
    client.blogPosts("hey.com");
    verify(builder).get("/blog/hey.com/posts", getApiKeyOptions());

    Map<String, Object> options = getRandomishOptions();
    client.blogPosts("hey.com", options);
    options.putAll(getApiKeyOptions());
    verify(builder).get("/blog/hey.com/posts", options);

    options = getRandomishOptions();
    options.put("type", "audio");
    client.blogPosts("hey.com", options);
    options.remove("type"); // should not be there on the request
    options.putAll(getApiKeyOptions());
    verify(builder).get("/blog/hey.com/posts/audio", options);
  }

  @Test
  public void blogPost() {
    final Long id = 24L;
    client.blogPost("hey.com", id);
    final Map<String, Object> options = getApiKeyOptions();
    options.put("id", id.toString());
    verify(builder).get("/blog/hey.com/posts", options);
  }

  @Test
  public void blogQueuedPosts() {
    client.blogQueuedPosts("hey.com");
    verify(builder).get("/blog/hey.com/posts/queue", null);

    final Map<String, Object> options = getRandomishOptions();
    client.blogQueuedPosts("hey.com", options);
    verify(builder).get("/blog/hey.com/posts/queue", options);
  }

  @Test
  public void blogDraftPosts() {
    client.blogDraftPosts("hey.com");
    verify(builder).get("/blog/hey.com/posts/draft", null);

    final Map<String, Object> options = getRandomishOptions();
    client.blogDraftPosts("hey.com", options);
    verify(builder).get("/blog/hey.com/posts/draft", options);
  }

  @Test
  public void blogSubmissions() {
    client.blogSubmissions("hey.com");
    verify(builder).get("/blog/hey.com/posts/submission", null);

    final Map<String, Object> options = getRandomishOptions();
    client.blogSubmissions("hey.com", options);
    verify(builder).get("/blog/hey.com/posts/submission", options);
  }

  /**
   * Post methods
   */

  @Test
  public void postDelete() {
    client.postDelete("hey.com", 42L);
    final Map<String, String> options = new HashMap<String, String>();
    options.put("id", "42");
    verify(builder).post("/blog/hey.com/post/delete", options);
  }

  @Test
  public void postReblog() {
    client.postReblog("hey.com", 42L, "key");
    Map<String, Object> options = new HashMap<String, Object>();
    options.put("id", "42");
    options.put("reblog_key", "key");
    verify(builder).post("/blog/hey.com/post/reblog", options);

    options = getRandomishOptions();
    client.postReblog("hey.com", 42L, "key", options);
    options.put("id", "42");
    options.put("reblog_key", "key");
    verify(builder).post("/blog/hey.com/post/reblog", options);
  }

  @Test
  public void postEdit() throws IOException {
    final Map<String, Object> options = getRandomishOptions();
    client.postEdit("hey.com", 42L, options);
    options.put("id", 42L);
    verify(builder).postMultipart("/blog/hey.com/post/edit", options);
  }

  @Test
  public void postCreate() throws IOException {
    final Map<String, Object> options = getRandomishOptions();
    client.postCreate("hey.com", options);
    verify(builder).postMultipart("/blog/hey.com/post", options);
  }

  /**
   * Tagged methods
   */

  @Test
  public void tagged() {
    final String tag = "coolio";

    client.tagged(tag);
    final Map<String, Object> map = getApiKeyOptions();
    map.put("tag", tag);
    verify(builder).get("/tagged", map);

    final Map<String, Object> options = getRandomishOptions();
    client.tagged(tag, options);
    options.putAll(getApiKeyOptions());
    options.put("tag", tag);
    verify(builder).get("/tagged", options);
  }

  /**
   * Other methods
   */

  @Test
  public void newPost() throws IllegalAccessException, InstantiationException {
    final QuotePost post = client.newPost("blog", QuotePost.class);
    assertEquals("blog", post.getBlogName());
    assertEquals(client, post.getClient());
  }

  @Test
  public void setToken() {
    client.setToken("t1", "t2");
    verify(builder).setToken("t1", "t2");
  }

  @Test
  public void xauth() {
    client.xauth("email", "pass");
    verify(builder).postXAuth("email", "pass");
  }

  /**
   * Helper methods
   */

  private static Map<String, Object> getApiKeyOptions() {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("api_key", "ck");
    return map;
  }

  private static Map<String, Object> getRandomishOptions() {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("hello", "world");
    return map;
  }

  private static Map<String, Object> getPostOptions() {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", 42L);
    map.putAll(getApiKeyOptions());
    return map;
  }

}
