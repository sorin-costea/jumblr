package com.tumblr.jumblr.types;

/**
 * This class represents a follower user on Tumblr
 *
 * @author jc
 */
public class Follower extends Resource {

  private String name;
  private boolean following;
  private String url;
  private Long updated;

  /**
   * Get the name for this User object
   *
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the blog url for this follower
   *
   * @return the url
   */
  public String getUrl() {
    return url;
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
   * Determine if this user is following
   *
   * @return An indication of following
   */
  public boolean isFollowing() {
    return following;
  }

}
