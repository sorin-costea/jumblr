package com.tumblr.jumblr.types;

public class Trail {

  private Blog blog;
  private Post post;
  private String content_raw, content;
  private Boolean is_root_item;

  /**
   * Gets the referenced blog
   *
   * @return the blog
   */
  public Blog getBlog() {
    return blog;
  }

  /**
   * Gets the referenced post
   *
   * @return the post
   */
  public Post getPost() {
    return post;
  }

  /**
   * Gets the contents in raw form aka with syling
   *
   * @return the content
   */
  public String getContentRaw() {
    return content_raw;
  }

  /**
   * Gets the content only html
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * Is it the root item?
   *
   * @return rootitem status
   */
  public Boolean getIsRootItem() {
    return is_root_item;
  }

}
