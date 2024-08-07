package com.tumblr.jumblr.types;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is the base of all post types on Tumblr
 *
 * @author jc
 */
public abstract class Post extends Resource {

  /**
   * Enum of valid post types.
   */
  public enum PostType {
    TEXT("text"),
    PHOTO("photo"),
    QUOTE("quote"),
    LINK("link"),
    CHAT("chat"),
    AUDIO("audio"),
    VIDEO("video"),
    ANSWER("answer"),
    POSTCARD("postcard"),
    UNKNOWN("unknown");

    private final String mType;

    PostType(final String type) {
      mType = type;
    }

    public String getValue() {
      return mType;
    }
  }

  protected PostType type;
  private Long id;
  private String author;
  private String reblog_key;
  private String blog_name;
  private String post_url, short_url, parent_post_url;
  private Long timestamp;
  private Long liked_timestamp;
  private String state;
  private String format;
  private String date;
  private List<String> tags;
  private boolean bookmarklet, mobile;
  private String source_url;
  private String source_title;
  private boolean liked;
  private String slug;
  private Long reblogged_from_id, reblogged_root_id;
  private String reblogged_from_url, reblogged_from_name, reblogged_from_title;
  private String reblogged_root_url, reblogged_root_name, reblogged_root_title;
  private Long note_count;
  private List<Note> notes;

  private List<Trail> trail;

  /**
   * Get the id of the author of the post
   *
   * @return possibly null author id
   */
  public String getAuthorId() {
    return author;
  }

  /**
   * Get whether or not this post is liked
   *
   * @return boolean
   */
  public boolean isLiked() {
    return liked;
  }

  /**
   * Get the source title for this post
   *
   * @return source title
   */
  public String getSourceTitle() {
    return source_title;
  }

  /**
   * Get the source URL for this post
   *
   * @return source URL
   */
  public String getSourceUrl() {
    return source_url;
  }

  /**
   * Get whether or not this post was from mobile
   *
   * @return boolean
   */
  public boolean isMobile() {
    return mobile;
  }

  /**
   * Get whether or not this post was from the bookmarklet
   *
   * @return boolean
   */
  public boolean isBookmarklet() {
    return bookmarklet;
  }

  /**
   * Get the format for this post
   *
   * @return the format
   */
  public String getFormat() {
    return format;
  }

  /**
   * Get the current state for this post
   *
   * @return the state; if set, one of `published`, `queued`, `draft`, or `private`
   */
  public String getState() {
    return state;
  }

  /**
   * Get the post URL for this post
   *
   * @return the URL
   */
  public String getPostUrl() {
    return post_url;
  }

  /**
   * Get the parent post URL for this post
   *
   * @return the parent URL
   */
  public String getParentPostUrl() {
    return parent_post_url;
  }

  /**
   * Get the short URL for this post
   *
   * @return the URL
   */
  public String getShortUrl() {
    return short_url;
  }

  /**
   * Get a list of the tags for this post
   *
   * @return the tags
   */
  public List<String> getTags() {
    return tags;
  }

  /**
   * Get the note count for this post
   *
   * @return the note count
   */
  public Long getNoteCount() {
    return note_count;
  }

  /**
   * Get date of this post as String
   *
   * @return date GMT string
   */
  public String getDateGMT() {
    return date;
  }

  /**
   * Get the timestamp of this post
   *
   * @return timestamp since epoch
   */
  public Long getTimestamp() {
    return timestamp;
  }

  /**
   * Get timestamp of when this post was liked
   *
   * @return the timestamp of when this post was liked
   */
  public Long getLikedTimestamp() {
    return liked_timestamp;
  }

  /**
   * Get the type of this post
   *
   * @return type as String
   */
  public PostType getType() {
    return PostType.UNKNOWN;
  }

  /**
   * Get this post's ID
   *
   * @return the ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Get the blog name
   *
   * @return the blog name for the post
   */
  public String getBlogName() {
    return blog_name;
  }

  /**
   * Get the reblog key
   *
   * @return the reblog key
   */
  public String getReblogKey() {
    return reblog_key;
  }

  /**
   * Get the slug
   *
   * @return possibly null reblog key
   */
  public String getSlug() {
    return slug;
  }

  /**
   * Get the ID of the post that this post reblogged
   *
   * @return the ID
   */
  public Long getRebloggedFromId() {
    return reblogged_from_id;
  }

  /**
   * Get name of the blog that this post reblogged
   *
   * @return the blog name for the post that this post reblogged
   */
  public String getRebloggedFromName() {
    return reblogged_from_name;
  }

  /**
   * @return the url for the post that this post reblogged.
   */
  public String getRebloggedFromUrl() {
    return reblogged_from_url;
  }

  /**
   * @return the title for the post that this post reblogged.
   */
  public String getRebloggedFromTitle() {
    return reblogged_from_title;
  }

  /**
   * @return the root id for the post that this post reblogged.
   */
  public Long getRebloggedRootId() {
    return reblogged_root_id;
  }

  /**
   * @return the root url for the post that this post reblogged.
   */
  public String getRebloggedRootUrl() {
    return reblogged_root_url;
  }

  /**
   * @return the root name for the post that this post reblogged.
   */
  public String getRebloggedRootName() {
    return reblogged_root_name;
  }

  /**
   * @return the root title for the post that this post reblogged.
   */
  public String getRebloggedRootTitle() {
    return reblogged_root_title;
  }

  /**
   * @return the reblog history list of this post.
   */
  public List<Trail> getTrail() {
    return trail;
  }

  /**
   * Get the notes on this post. You must set "notes_info" to "true" in the options map for this to work.
   *
   * @return a copy of the array of the notes on this post
   */
  public List<Note> getNotes() {
    return notes;
  }

  /**
   * Delete this post
   */
  public void delete() {
    client.postDelete(blog_name, id);
  }

  /**
   * Reblog this post
   *
   * @param blogName
   *          the blog name to reblog onto
   * @param options
   *          options to reblog with (or null)
   *
   * @return reblogged post
   */
  public Post reblog(final String blogName, final Map<String, ?> options) {
    return client.postReblog(blogName, id, reblog_key, options);
  }

  public Post reblog(final String blogName) {
    return this.reblog(blogName, null);
  }

  /**
   * Like this post
   */
  public void like() {
    client.like(id, reblog_key);
  }

  /**
   * Unlike this post
   */
  public void unlike() {
    client.unlike(id, reblog_key);
  }

  /**
   * Set the blog name for this post
   *
   * @param blogName
   *          the blog name to set
   */
  public void setBlogName(final String blogName) {
    blog_name = blogName;
  }

  /**
   * Set the id for this post
   *
   * @param id
   *          The id of the post
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Set the format
   *
   * @param format
   *          the format
   */
  public void setFormat(final String format) {
    this.format = format;
  }

  /**
   * Set the slug
   *
   * @param slug
   *          the post url slug
   */
  public void setSlug(final String slug) {
    this.slug = slug;
  }

  /**
   * Set the data as a string
   *
   * @param dateString
   *          the date to set
   */
  public void setDate(final String dateString) {
    date = dateString;
  }

  /**
   * Set the date as a date
   *
   * @param date
   *          the date to set
   */
  public void setDate(final Date date) {
    final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    setDate(df.format(date));
  }

  /**
   * Set the state for this post
   *
   * @param state
   *          the state; one of `published`, `queued`, `draft`, or `private`. Tumblr API defaults to `published` if not
   *          specified.
   */
  public void setState(final String state) {
    this.state = state;
  }

  /**
   * Set the tags for this post
   *
   * @param tags
   *          the tags
   */
  public void setTags(final List<String> tags) {
    this.tags = tags;
  }

  /**
   * Add a tag
   *
   * @param tag
   *          the tag
   */
  public void addTag(final String tag) {
    if (tags == null) {
      tags = new ArrayList<String>();
    }
    tags.add(tag);
  }

  /**
   * Remove a tag
   *
   * @param tag
   *          the tag
   */
  public void removeTag(final String tag) {
    tags.remove(tag);
  }

  /**
   * Save this post
   *
   * @throws IOException
   *           if a file in detail cannot be read
   */
  public void save() throws IOException {
    if (id == null) {
      id = client.postCreate(blog_name, detail());
    } else {
      client.postEdit(blog_name, id, detail());
    }
  }

  /**
   * Detail for this post
   *
   * @return the detail
   */
  protected Map<String, Object> detail() {
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put("state", state);
    map.put("tags", getTagString());
    map.put("format", format);
    map.put("slug", slug);
    map.put("date", date);
    map.put("type", getType().getValue());
    return map;
  }

  /**
   * Get the tags as a string
   *
   * @return a string of CSV tags
   */
  private String getTagString() {
    return tags == null ? "" : StringUtils.join(tags.toArray(new String[0]), ",");
  }

  /**
   * Post toString
   *
   * @return a nice representation of this post
   */
  @Override
  public String toString() {
    return "[" + this.getClass().getName() + " (" + blog_name + ":" + id + ")]";
  }

}
