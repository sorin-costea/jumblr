package com.tumblr.jumblr.types;

import java.util.List;

/**
 * This class represents a User on Tumblr
 * 
 * @author jc
 */
public class User extends Resource {

  private List<Blog> blogs;
  private String name;
  private Object following;
  private Integer likes;
  private String default_post_format;
  private Long updated;

  /**
   * Return the default post format for this user
   * 
   * @return String format
   */
  public String getDefaultPostFormat() {
    return default_post_format;
  }

  /**
   * Get the name for this User object
   * 
   * @return The name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the number of likes for this user
   * 
   * @return the likes count
   */
  public Integer getLikeCount() {
    return this.likes;
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
   * Get the number of users this user is following
   * 
   * @return The following count
   */
  public Integer getFollowingCount() {
    return following instanceof Boolean ? null : ((Double) following).intValue();
  }

  /**
   * Determine if this user is following
   * 
   * @return An indication of following
   */
  public Boolean isFollowing() {
    return following instanceof Boolean ? (Boolean) following : null;
  }

  /**
   * Get the blog List for this user
   * 
   * @return The blog List for this user
   */
  public List<Blog> getBlogs() {
    if (blogs != null) {
      for (final Blog blog : blogs) {
        blog.setClient(client);
      }
    }
    return this.blogs;
  }

}
