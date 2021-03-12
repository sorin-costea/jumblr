package com.tumblr.jumblr.types;

import java.util.List;
import java.util.Map;

/**
 * This class represents an individual Tumbelog
 *
 * @author jc
 */
public class Blog extends Resource {

  private String name;
  private String title;
  private String description;
  private int posts, likes, followers;
  private Long updated;
  private boolean ask, ask_anon, followed;

  /**
   * Get the description of this blog
   *
   * @return String description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Can we ask questions on this blog?
   *
   * @return boolean
   */
  public boolean canAsk() {
    return ask;
  }

  /**
   * Can we ask questions on this blog anonymously?
   *
   * @return boolean
   */
  public boolean canAskAnonymously() {
    return ask_anon;
  }

  /**
   * Is this blog followed?
   *
   * @return boolean
   */
  public boolean isFollowed() {
    return followed;
  }

  /**
   * Get the number of posts for this blog
   *
   * @return int the number of posts
   */
  public Integer getPostCount() {
    return posts;
  }

  /**
   * Get the number of likes for this blog
   *
   * @return int the number of likes
   */
  public Integer getLikeCount() {
    return likes;
  }

  /**
   * Get the time of the most recent post (in seconds since epoch)
   *
   * @return Long of time
   */
  public Long getUpdated() {
    return updated;
  }

  /**
   * Get the title of this blog
   *
   * @return The title of the blog
   */
  public String getTitle() {
    return title;
  }

  /**
   * Get the name of this blog
   *
   * @return The name of the blog
   */
  public String getName() {
    return name;
  }

  /**
   * Get the avatar for this blog (of a given size
   *
   * @param size
   *          the size to get the avatar for
   *
   * @return A string URL for the avatar
   */
  public String avatar(final Integer size) {
    return client.blogAvatar(name, size);
  }

  public String avatar() {
    return this.avatar(null);
  }

  public Integer getFollowersCount() {
    return followers;
  }

  /**
   * Get followers for this blog
   *
   * @param options
   *          a map of options (or null)
   *
   * @return A List of users
   */
  public List<User> followers(final Map<String, ?> options) {
    return client.blogFollowers(name, options);
  }

  public List<User> followers() {
    return this.followers(null);
  }

  /**
   * Get the posts for this blog
   *
   * @param options
   *          a map of options (or null)
   *
   * @return A List of posts
   */
  public List<Post> posts(final Map<String, ?> options) {
    return client.blogPosts(name, options);
  }

  public List<Post> posts() {
    return this.posts(null);
  }

  /**
   * Get an individual post by id
   *
   * @param postId
   *          the id of the post to retrieve
   *
   * @return the post (or null)
   */
  public Post getPost(final Long postId) {
    return client.blogPost(name, postId);
  }

  /**
   * Get likes posts for this blog
   *
   * @param options
   *          a map of options (or null)
   *
   * @return A List of posts
   */
  public List<Post> likedPosts(final Map<String, ?> options) {
    return client.blogLikes(name, options);
  }

  public List<Post> likedPosts() {
    return this.likedPosts(null);
  }

  /**
   * Follow this blog
   */
  public void follow() {
    client.follow(name);
  }

  /**
   * Unfollow this blog
   */
  public void unfollow() {
    client.unfollow(name);
  }

  /**
   * Get the queued posts for this blog
   *
   * @param options
   *          the options (or null)
   *
   * @return a List of posts
   */
  public List<Post> queuedPosts(final Map<String, ?> options) {
    return client.blogQueuedPosts(name, options);
  }

  public List<Post> queuedPosts() {
    return this.queuedPosts(null);
  }

  /**
   * Get the draft posts for this blog
   *
   * @param options
   *          the options (or null)
   *
   * @return a List of posts
   */
  public List<Post> draftPosts(final Map<String, ?> options) {
    return client.blogDraftPosts(name, options);
  }

  public List<Post> draftPosts() {
    return this.draftPosts(null);
  }

  /**
   * Get the submissions for this blog
   *
   * @param options
   *          the options (or null)
   *
   * @return a List of posts
   */
  public List<Post> submissions(final Map<String, ?> options) {
    return client.blogSubmissions(name, options);
  }

  public List<Post> submissions() {
    return this.submissions(null);
  }

  /**
   * Get the activity feed for this blog
   *
   * @param options
   *          a map of options (or null)
   *
   * @return Notifications object
   */
  public Notifications notifications(final Map<String, ?> options) {
    return client.blogNotifications(name, options);
  }

  public Notifications notifications() {
    return this.notifications(null);
  }

  /**
   * Create a new post of a given type for this blog
   *
   * @param klass
   *          the class of the post to make
   * @param <T>
   *          the class of the post to make
   *
   * @return new post
   *
   * @throws IllegalAccessException
   *           if class instantiation fails
   * @throws InstantiationException
   *           if class instantiation fails
   */
  public <T extends Post> T newPost(final Class<T> klass) throws IllegalAccessException, InstantiationException {
    return client.newPost(name, klass);
  }

  /**
   * Set the name of this blog
   *
   * @param name
   *          the name to set
   */
  public void setName(final String name) {
    this.name = name;
  }

}
