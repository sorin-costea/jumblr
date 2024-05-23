package com.tumblr.jumblr.types;

import java.io.File;
import java.util.List;

/**
 * This class represents a Photo in a PhotoPost
 *
 * @author jc
 */
public class Photo {

  /**
   * Types of the post - what kind of data does it have?
   */
  public enum PhotoType {
    SOURCE("source"),
    FILE("data");

    private final String prefix;

    PhotoType(final String prefix) {
      this.prefix = prefix;
    }

    public String getPrefix() {
      return prefix;
    }
  }

  private String caption;
  private List<PhotoSize> alt_sizes;
  private PhotoSize original_size;

  private String source;
  private File file;

  /**
   * Create a new photo with a data
   *
   * @param file
   *          the file for the photo
   */
  public Photo(final File file) {
    this.file = file;
  }

  /**
   * Create a new photo with a source
   *
   * @param source
   *          the source for the photo
   */
  public Photo(final String source) {
    this.source = source;
  }

  public Photo(final String caption, final PhotoSize origSize, final List<PhotoSize> photoSizes) {
    this.caption = caption;
    original_size = origSize;
    alt_sizes = photoSizes;
  }

  /**
   * Get the type of this photo
   *
   * @return PhotoType the type of photo
   */
  public PhotoType getType() {
    if (source != null) {
      return PhotoType.SOURCE;
    }
    if (file != null) {
      return PhotoType.FILE;
    }
    return null;
  }

  /**
   * Get the sizes of this Photo
   *
   * @return PhotoSize[] sizes
   */
  public List<PhotoSize> getSizes() {
    return alt_sizes;
  }

  /**
   * Get the original sized photo
   *
   * @return the original sized PhotoSize
   */
  public PhotoSize getOriginalSize() {
    return original_size;
  }

  /**
   * Get the caption of this photo
   *
   * @return the caption
   */
  public String getCaption() {
    return caption;
  }

  /**
   * Get the detail for this photo
   *
   * @return the detail (String or File)
   */
  protected Object getDetail() {
    if (source != null) {
      return source;
    }
    return file;
  }

}
