package com.tumblr.jumblr.types;

import java.io.File;
import java.util.Map;

/**
 * This class represents a post of type "audio"
 *
 * @author jc
 */
public class AudioPost extends SafePost {

  private String caption;
  private final String player;
  private final String audio_url;
  private final Integer plays;
  private final String album_art, artist, album, track_name;
  private final Integer track_number, year;

  private File data;
  private String external_url;

  /**
   * Get the audio URL for this post
   *
   * @return the audio URL
   */
  public String getAudioUrl() {
    return audio_url;
  }

  /**
   * Get the play count for this post
   *
   * @return the play count
   */
  public Integer getPlayCount() {
    return plays;
  }

  /**
   * Get the track name for this post
   *
   * @return the track name
   */
  public String getTrackName() {
    return track_name;
  }

  /**
   * Get the album for this post
   *
   * @return the album name
   */
  public String getAlbumName() {
    return album;
  }

  /**
   * Get the artist for this post
   *
   * @return the artist
   */
  public String getArtistName() {
    return artist;
  }

  /**
   * Get the album art for this post
   *
   * @return the album art
   */
  public String getAlbumArtUrl() {
    return album_art;
  }

  /**
   * Get the year for this post
   *
   * @return the year
   */
  public Integer getYear() {
    return year;
  }

  /**
   * Get the track number for this post
   *
   * @return the track number
   */
  public Integer getTrackNumber() {
    return track_number;
  }

  /**
   * Get the caption for this post
   *
   * @return the caption
   */
  public String getCaption() {
    return caption;
  }

  /**
   * Get the embed code for this Post
   *
   * @return the embed code
   */
  public String getEmbedCode() {
    return player;
  }

  /**
   * Set the external url for this post
   *
   * @param url
   *          the external url
   *
   * @throws IllegalArgumentException
   *           when data is already set
   */
  public void setExternalUrl(final String url) {
    if (data != null) {
      throw new IllegalArgumentException("Cannot provide both data & external_url");
    }
    external_url = url;
  }

  /**
   * Set the data for this post
   *
   * @param file
   *          the file to read from
   *
   * @throws IllegalArgumentException
   *           source is already set
   */
  public void setData(final File file) {
    if (external_url != null) {
      throw new IllegalArgumentException("Cannot supply both externalUrl & data");
    }
    data = file;
  }

  /**
   * Set the caption for this post
   *
   * @param caption
   *          the caption
   */
  public void setCaption(final String caption) {
    this.caption = caption;
  }

  @Override
  public PostType getType() {
    return PostType.AUDIO;
  }

  /**
   * Get the details about this post (along with base details)
   *
   * @return the detail
   */
  @Override
  public Map<String, Object> detail() {
    final Map<String, Object> details = super.detail();
    details.put("caption", caption);
    details.put("data", data);
    details.put("external_url", external_url);
    return details;
  }

  public AudioPost(final String caption, final String player, final String audioUrl, final Integer plays,
      final String albumArt, final String artist, final String album, final String trackName, final Integer trackNumber,
      final Integer year) {
    this.caption = caption;
    this.player = player;
    audio_url = audioUrl;
    this.plays = plays;
    album_art = albumArt;
    this.artist = artist;
    this.album = album;
    track_name = trackName;
    track_number = trackNumber;
    this.year = year;
  }
}
