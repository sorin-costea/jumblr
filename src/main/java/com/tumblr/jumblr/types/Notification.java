package com.tumblr.jumblr.types;

import java.util.List;

public class Notification {

  private String type;
  private Long timestamp, before;
  private Long target_post_id;
  private String from_tumblelog_name, target_tumblelog_name;
  private Long post_id;
  private Boolean from_tumblelog_is_adult, followed;
  private List<String> post_tags;
  private String target_post_summary, added_text, reply_text;

  /**
   * The type of activity item, from the list above.
   *
   * @return notification type
   */
  public String getType() {
    return type;
  }

  /**
   * A unix epoch timestamp of when the event happened.
   *
   * @return timestamp
   */
  public Long getTimestamp() {
    return timestamp;
  }

  /**
   * If the activity has to do with one of your blog's posts, this will be its ID.
   *
   * @return target id
   */
  public Long getTargetPostId() {
    return target_post_id;
  }

  /**
   * If the activity is coming from another blog, like a Like or Reblog, this will be its name.
   *
   * @return blog name
   */
  public String getFromTumblelogName() {
    return from_tumblelog_name;
  }

  /**
   * For activity like Reblogs and Replies, this will be the relevant post's ID.
   *
   * @return id
   */
  public Long getPostId() {
    return post_id;
  }

  /**
   * An array of tags used in the reblog, if any.
   *
   * @return tags list
   */
  public List<String> getPostTags() {
    return post_tags;
  }

  /**
   * For reblogs with comment, this will be a summary of the added content.
   *
   * @return comment text
   */
  public String getAddedText() {
    return added_text;
  }

  /**
   * For replies, this will be the text of the reply.
   *
   * @return reply text
   */
  public String getReplyText() {
    return reply_text;
  }

  /**
   * @return the before
   */
  public Long getBefore() {
    return before;
  }

  /**
   * @return the target_post_summary
   */
  public String getTargetPostSummary() {
    return target_post_summary;
  }

  /**
   * @return the target_tumblelog_name
   */
  public String getTargetTumblelogName() {
    return target_tumblelog_name;
  }

  /**
   * @return the from_tumblelog_is_adult
   */
  public Boolean getFromTumblelogIsAdult() {
    return from_tumblelog_is_adult;
  }

  /**
   * @return the followed
   */
  public Boolean getFollowed() {
    return followed;
  }

}
