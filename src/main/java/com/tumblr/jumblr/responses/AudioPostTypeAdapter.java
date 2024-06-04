package com.tumblr.jumblr.responses;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.UnknownTypePost;

/**
 * A java-io.File cannot be deserialized here, and a returned Photo will of course have no file anyway, so shouldn't
 * even try to deserialize it.
 *
 */
public class AudioPostTypeAdapter extends TypeAdapter<AudioPost> {

  private final Gson gson = new Gson();

  @Override
  public void write(final JsonWriter out, final AudioPost value) throws IOException {
    out.beginObject();

    final JsonObject parentJson = gson.toJsonTree(value, UnknownTypePost.class).getAsJsonObject();
    for (final Map.Entry<String, JsonElement> entry : parentJson.entrySet()) {
      out.name(entry.getKey());
      gson.toJson(entry.getValue(), out);
    }
    out.name("caption").value(value.getCaption());
    out.name("player").value(value.getEmbedCode());
    out.name("audio_url").value(value.getAudioUrl());
    out.name("plays").value(value.getPlayCount());
    out.name("album_art").value(value.getAlbumArtUrl());
    out.name("artist").value(value.getArtistName());
    out.name("album").value(value.getAlbumName());
    out.name("track_name").value(value.getTrackName());
    out.name("track_number").value(value.getTrackNumber());
    out.name("year").value(value.getYear());

    out.endObject();
  }

  @Override
  public AudioPost read(final JsonReader in) throws IOException {
    final JsonObject jsonObject = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();

    final String caption = jsonObject.has("caption") ? jsonObject.get("caption").getAsString() : null;
    final String player = jsonObject.has("player") ? jsonObject.get("player").getAsString() : null;
    final String audioUrl = jsonObject.has("audio_url") ? jsonObject.get("audio_url").getAsString() : null;
    final Integer plays = jsonObject.has("plays") ? jsonObject.get("plays").getAsInt() : null;
    final String albumArt = jsonObject.has("album_art") ? jsonObject.get("album_art").getAsString() : null;
    final String artist = jsonObject.has("artist") ? jsonObject.get("artist").getAsString() : null;
    final String album = jsonObject.has("album") ? jsonObject.get("album").getAsString() : null;
    final String trackName = jsonObject.has("track_name") ? jsonObject.get("track_name").getAsString() : null;
    final Integer trackNumber = jsonObject.has("track_number") ? jsonObject.get("track_number").getAsInt() : null;
    final Integer year = jsonObject.has("year") ? jsonObject.get("year").getAsInt() : null;

    final AudioPost childClass = new AudioPost(caption, player, audioUrl, plays, albumArt, artist, album, trackName,
        trackNumber, year);
    final UnknownTypePost parentClass = gson.fromJson(jsonObject, UnknownTypePost.class);
    for (final Field field : UnknownTypePost.class.getDeclaredFields()) {
      try {
        field.setAccessible(true);
        field.set(childClass, field.get(parentClass));
      } catch (final IllegalAccessException e) {
        throw new JsonParseException("Failed to set parent fields to child", e);
      }
    }
    return childClass;
  }
}
