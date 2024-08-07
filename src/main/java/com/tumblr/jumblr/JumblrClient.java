package com.tumblr.jumblr;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tumblr.jumblr.request.RateLimits;
import com.tumblr.jumblr.request.RequestBuilder;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Follower;
import com.tumblr.jumblr.types.Limit;
import com.tumblr.jumblr.types.Notes;
import com.tumblr.jumblr.types.Notifications;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;
import org.scribe.model.Token;

/**
 * This is the base JumblrClient that is used to make requests to the Tumblr API. All calls that can be made from other
 * Resource(s) can be made from here.
 *
 * @author jc
 */
public class JumblrClient {

  private RequestBuilder requestBuilder;
  private String apiKey;

  public JumblrClient() {
    requestBuilder = new RequestBuilder(this);
  }

  /**
   * Instantiate a new Jumblr Client with no token
   *
   * @param consumerKey
   *          The consumer key for the client
   * @param consumerSecret
   *          The consumer secret for the client
   */
  public JumblrClient(final String consumerKey, final String consumerSecret) {
    this();
    requestBuilder.setConsumer(consumerKey, consumerSecret);
    apiKey = consumerKey;
  }

  /**
   * Instantiate a new Jumblr Client
   *
   * @param consumerKey
   *          The consumer key for the client
   * @param consumerSecret
   *          The consumer secret for the client
   * @param token
   *          The token for the client
   * @param tokenSecret
   *          The token secret for the client
   */
  public JumblrClient(final String consumerKey, final String consumerSecret, final String token,
      final String tokenSecret) {
    this(consumerKey, consumerSecret);
    this.setToken(token, tokenSecret);
  }

  /**
   * Set the token for this client
   *
   * @param token
   *          The token for the client
   * @param tokenSecret
   *          The token secret for the client
   */
  public void setToken(final String token, final String tokenSecret) {
    requestBuilder.setToken(token, tokenSecret);
  }

  /**
   * Set the token for this client.
   *
   * @param token
   *          The token for the client.
   */
  public void setToken(final Token token) {
    requestBuilder.setToken(token);
  }

  /**
   * Performs an XAuth authentication.
   *
   * @param email
   *          the user's login email.
   * @param password
   *          the user's login password.
   */
  public void xauth(final String email, final String password) {
    setToken(requestBuilder.postXAuth(email, password));
  }

  /**
   * Get the user info for the authenticated User
   *
   * @return The authenticated user
   */
  public User user() {
    return requestBuilder.get("/user/info", null).getUser();
  }

  /**
   * Get the user dashboard for the authenticated User
   *
   * @param options
   *          the options for the call (or null)
   *
   * @return A List of posts
   */
  public List<Post> userDashboard(final Map<String, ?> options) {
    return requestBuilder.get("/user/dashboard", options).getPosts();
  }

  public List<Post> userDashboard() {
    return this.userDashboard(null);
  }

  public Map<String, Limit> userLimits(final Map<String, ?> options) {
    return requestBuilder.get("/user/limits", options).getUserLimits().getUser();
  }

  public Map<String, Limit> userLimits() {
    return this.userLimits(null);
  }

  /**
   * Get the blogs the given user is following
   *
   * @param options
   *          the options
   *
   * @return a List of blogs
   */
  public List<Blog> userFollowing(final Map<String, ?> options) {
    return requestBuilder.get("/user/following", options).getBlogs();
  }

  public List<Blog> userFollowing() {
    return this.userFollowing(null);
  }

  /**
   * Tagged posts
   *
   * @param tag
   *          the tag to search
   * @param options
   *          the options for the call (or null)
   *
   * @return a list of posts
   */
  public List<Post> tagged(final String tag, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);
    soptions.put("tag", tag);
    return requestBuilder.get("/tagged", soptions).getTaggedPosts();
  }

  public List<Post> tagged(final String tag) {
    return this.tagged(tag, null);
  }

  /**
   * Get the blog info for a given blog
   *
   * @param blogName
   *          the Name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return The Blog object for this blog
   */
  public Blog blogInfo(final String blogName, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/info"), soptions).getBlog();
  }

  public Blog blogInfo(final String blogName) {
    return this.blogInfo(blogName, null);
  }

  /**
   * Get the followers for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return the blog object for this blog
   */
  public List<Follower> blogFollowers(final String blogName, final Map<String, ?> options) {
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/followers"), options).getFollowers();
  }

  public List<Follower> blogFollowers(final String blogName) {
    return this.blogFollowers(blogName, null);
  }

  /**
   * Get the public likes for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> blogLikes(final String blogName, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/likes"), soptions).getLikedPosts();
  }

  public List<Post> blogLikes(final String blogName) {
    return this.blogLikes(blogName, null);
  }

  /**
   * Get the activity feed for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of notifications
   */
  public Notifications blogNotifications(final String blogName, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/notifications"), soptions).getNotifications();
  }

  public Notifications blogNotifications(final String blogName) {
    return this.blogNotifications(blogName, null);
  }

  /**
   * Get the posts for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> blogPosts(final String blogName, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);

    final StringBuilder path = new StringBuilder("/posts");
    if (soptions.containsKey("type")) {
      path.append("/").append(soptions.get("type").toString());
      soptions.remove("type");
    }
    return requestBuilder.get(JumblrClient.blogPath(blogName, path.toString()), soptions).getPosts();
  }

  public List<Post> blogPosts(final String blogName) {
    return this.blogPosts(blogName, null);
  }

  /**
   * Get an individual post by id
   *
   * @param blogName
   *          the name of the blog
   * @param postId
   *          the id of the post to get
   * @param options
   *          the options for this call (or null)
   *
   * @return the Post or null
   */
  public Post blogPost(final String blogName, final Long postId, Map<String, String> options) {
    if (options == null) {
      options = new HashMap<String, String>();
    }
    options.put("id", postId.toString());
    final List<Post> posts = this.blogPosts(blogName, options);
    return posts.size() > 0 ? posts.get(0) : null;
  }

  public Post blogPost(final String blogName, final Long postId) {
    return blogPost(blogName, postId, null);
  }

  /**
   * Get the queued posts for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> blogQueuedPosts(final String blogName, final Map<String, ?> options) {
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/posts/queue"), options).getPosts();
  }

  public List<Post> blogQueuedPosts(final String blogName) {
    return this.blogQueuedPosts(blogName, null);
  }

  /**
   * Get the draft posts for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> blogDraftPosts(final String blogName, final Map<String, ?> options) {
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/posts/draft"), options).getPosts();
  }

  public List<Post> blogDraftPosts(final String blogName) {
    return this.blogDraftPosts(blogName, null);
  }

  /**
   * Get the submissions for a given blog
   *
   * @param blogName
   *          the name of the blog
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> blogSubmissions(final String blogName, final Map<String, ?> options) {
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/posts/submission"), options).getPosts();
  }

  public List<Post> blogSubmissions(final String blogName) {
    return this.blogSubmissions(blogName, null);
  }

  /**
   * Get the likes for the authenticated user
   *
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of posts
   */
  public List<Post> userLikes(final Map<String, ?> options) {
    return requestBuilder.get("/user/likes", options).getLikedPosts();
  }

  public List<Post> userLikes() {
    return this.userLikes(null);
  }

  /**
   * Check if one of your blogs is followed by another blog
   *
   * @param blogName
   *          the name of your blog
   * @param otherBlogName
   *          the name of the blog to check
   *
   * @return a boolean, true when the queried blog follows your blog
   */
  public boolean blogFollowedBy(final String blogName, final String otherBlogName) {
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/followed_by?query=" + otherBlogName), null)
        .getFollowedBy();
  }

  /**
   * Get a specific size avatar for a given blog
   *
   * @param blogName
   *          the avatar URL of the blog
   * @param size
   *          The size requested
   *
   * @return a string representing the URL of the avatar
   */
  public String blogAvatar(final String blogName, final Integer size) {
    final String pathExt = size == null ? "" : "/" + size.toString();
    return requestBuilder.getRedirectUrl(JumblrClient.blogPath(blogName, "/avatar" + pathExt));
  }

  public String blogAvatar(final String blogName) {
    return this.blogAvatar(blogName, null);
  }

  /**
   * Like a given post
   *
   * @param postId
   *          the ID of the post to like
   * @param reblogKey
   *          The reblog key for the post
   */
  public void like(final Long postId, final String reblogKey) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("id", postId.toString());
    map.put("reblog_key", reblogKey);
    requestBuilder.post("/user/like", map);
  }

  /**
   * Unlike a given post
   *
   * @param postId
   *          the ID of the post to unlike
   * @param reblogKey
   *          The reblog key for the post
   */
  public void unlike(final Long postId, final String reblogKey) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("id", postId.toString());
    map.put("reblog_key", reblogKey);
    requestBuilder.post("/user/unlike", map);
  }

  /**
   * Follow a given blog
   *
   * @param blogName
   *          The name of the blog to follow
   *
   * @return
   */
  public Blog follow(final String blogName) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("url", JumblrClient.blogUrl(blogName));
    return requestBuilder.post("/user/follow", map).getBlog();
  }

  /**
   * Unfollow a given blog
   *
   * @param blogName
   *          the name of the blog to unfollow
   *
   * @return
   */
  public Blog unfollow(final String blogName) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("url", JumblrClient.blogUrl(blogName));
    return requestBuilder.post("/user/unfollow", map).getBlog();
  }

  /**
   * Delete a given post
   *
   * @param blogName
   *          the name of the blog the post is in
   * @param postId
   *          the id of the post to delete
   */
  public void postDelete(final String blogName, final Long postId) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("id", postId.toString());
    requestBuilder.post(JumblrClient.blogPath(blogName, "/post/delete"), map);
  }

  /**
   * Reblog a given post
   *
   * @param blogName
   *          the name of the blog to post to
   * @param postId
   *          the id of the post
   * @param reblogKey
   *          the reblog_key of the post
   * @param options
   *          Additional options (or null)
   *
   * @return The created reblog Post or null
   */
  public Post postReblog(final String blogName, final Long postId, final String reblogKey, Map<String, ?> options) {
    if (options == null) {
      options = new HashMap<String, String>();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("id", postId.toString());
    soptions.put("reblog_key", reblogKey);
    final Long reblogId = requestBuilder.post(JumblrClient.blogPath(blogName, "/post/reblog"), soptions).getId();
    return blogPost(blogName, reblogId);
  }

  /**
   * Reblog a given post
   *
   * @param blogName
   *          the name of the blog to post to
   * @param postId
   *          the id of the post
   * @param reblogKey
   *          the reblog_key of the post
   *
   * @return The created reblog Post or null
   */
  public Post postReblog(final String blogName, final Long postId, final String reblogKey) {
    return this.postReblog(blogName, postId, reblogKey, null);
  }

  /**
   * Save edits for a given post
   *
   * @param blogName
   *          The blog name of the post
   * @param id
   *          the Post id
   * @param detail
   *          The detail to save
   *
   * @throws IOException
   *           if any file specified in detail cannot be read
   */
  public void postEdit(final String blogName, final Long id, final Map<String, ?> detail) throws IOException {
    final Map<String, Object> sdetail = JumblrClient.safeOptionMap(detail);
    sdetail.put("id", id);
    requestBuilder.postMultipart(JumblrClient.blogPath(blogName, "/post/edit"), sdetail);
  }

  /**
   * Create a post
   *
   * @param blogName
   *          The blog name for the post
   * @param detail
   *          the detail to save
   *
   * @return Long the created post's id
   *
   * @throws IOException
   *           if any file specified in detail cannot be read
   */
  public Long postCreate(final String blogName, final Map<String, ?> detail) throws IOException {
    return requestBuilder.postMultipart(JumblrClient.blogPath(blogName, "/post"), detail).getId();
  }

  /**
   * Set up a new post of a given type
   *
   * @param blogName
   *          the name of the blog for this post (or null)
   * @param klass
   *          the type of Post to instantiate
   * @param <T>
   *          the type of Post to instantiate
   *
   * @return the new post with the client set
   *
   * @throws IllegalAccessException
   *           if class instantiation fails
   * @throws InstantiationException
   *           if class instantiation fails
   */
  public <T extends Post> T newPost(final String blogName, final Class<T> klass)
      throws IllegalAccessException, InstantiationException {
    final T post = klass.newInstance();
    post.setClient(this);
    post.setBlogName(blogName);
    return post;
  }

  /**
   * Get the notes list for a given post
   *
   * @param blogName
   *          The blog name of the post
   * @param id
   *          the Post id
   * @param options
   *          the options for this call (or null)
   *
   * @return a List of notes
   */
  public Notes blogPostNotes(final String blogName, final Long id, Map<String, ?> options) {
    if (options == null) {
      options = Collections.emptyMap();
    }
    final Map<String, Object> soptions = JumblrClient.safeOptionMap(options);
    soptions.put("api_key", apiKey);
    soptions.put("id", id);
    return requestBuilder.get(JumblrClient.blogPath(blogName, "/notes"), soptions).getNotes();
  }

  public Notes blogPostNotes(final String blogName, final Long id) {
    return this.blogPostNotes(blogName, id, null);
  }

  /**
   **
   **
   */

  private static String blogPath(final String blogName, final String extPath) {
    return "/blog/" + blogUrl(blogName) + extPath;
  }

  private static String blogUrl(final String blogName) {
    return blogName.contains(".") ? blogName : blogName + ".tumblr.com";
  }

  public void setRequestBuilder(final RequestBuilder builder) {
    requestBuilder = builder;
  }

  public RequestBuilder getRequestBuilder() {
    return requestBuilder;
  }

  public RateLimits getRateLimits() {
    return requestBuilder.getRateLimits();
  }

  private static Map<String, Object> safeOptionMap(final Map<String, ?> map) {
    return new HashMap<String, Object>(map);
  }

}
