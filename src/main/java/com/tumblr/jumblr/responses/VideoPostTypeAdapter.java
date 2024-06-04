package com.tumblr.jumblr.responses;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.tumblr.jumblr.types.UnknownTypePost;
import com.tumblr.jumblr.types.Video;
import com.tumblr.jumblr.types.VideoPost;

/**
 * A java-io.File cannot be deserialized here, and a returned Photo will of course have no file anyway, so shouldn't
 * even try to deserialize it.
 *
 */
public class VideoPostTypeAdapter extends TypeAdapter<VideoPost> {

  private final Gson gson = new Gson();

  @Override
  public void write(final JsonWriter out, final VideoPost value) throws IOException {
    out.beginObject();

    final JsonObject parentJson = gson.toJsonTree(value, UnknownTypePost.class).getAsJsonObject();
    for (final Map.Entry<String, JsonElement> entry : parentJson.entrySet()) {
      out.name(entry.getKey());
      gson.toJson(entry.getValue(), out);
    }
    out.name("caption").value(value.getCaption());
    out.name("permalink_url").value(value.getPermalinkUrl());
    out.name("thumbnail_url").value(value.getThumbnailUrl());
    out.name("thumbnail_width").value(value.getThumbnailWidth());
    out.name("thumbnail_height").value(value.getThumbnailHeight());

    out.name("player");
    final Type videoListType = new TypeToken<List<Video>>() {}.getType();
    gson.toJson(value.getVideos(), videoListType, out);

    out.endObject();
  }

  @Override
  public VideoPost read(final JsonReader in) throws IOException {
    final JsonObject jsonObject = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();

    final String caption = jsonObject.has("caption") ? jsonObject.get("caption").getAsString() : null;
    final String permalink_url = jsonObject.has("permalink_url") ? jsonObject.get("permalink_url").getAsString() : null;
    final String thumbnail_url = jsonObject.has("thumbnail_url") ? jsonObject.get("thumbnail_url").getAsString() : null;
    final int thumbnail_width = jsonObject.has("thumbnail_width") ? jsonObject.get("thumbnail_width").getAsInt() : 0;
    final int thumbnail_height = jsonObject.has("thumbnail_height") ? jsonObject.get("thumbnail_height").getAsInt() : 0;

    List<Video> videoList = null;
    if (jsonObject.has("player")) {
      final Type videoListType = new TypeToken<List<Video>>() {}.getType();
      videoList = gson.fromJson(jsonObject.get("player"), videoListType);
    }

    final VideoPost childClass = new VideoPost(caption, permalink_url, thumbnail_url, thumbnail_width, thumbnail_height,
        videoList);
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
