package com.tumblr.jumblr.responses;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoSize;

/**
 * A java-io.File cannot be deserialized here, and a returned Photo will of course have no file anyway, so shouldn't
 * even try to deserialize it.
 *
 */
public class PhotoTypeAdapter extends TypeAdapter<Photo> {

  private final Gson gson = new Gson();

  @Override
  public void write(final JsonWriter out, final Photo value) throws IOException {
    out.beginObject();
    out.name("caption").value(value.getCaption());

    out.name("original_size");
    final Type photoSizeType = new TypeToken<PhotoSize>() {}.getType();
    gson.toJson(value.getOriginalSize(), photoSizeType, out);
    out.name("alt_sizes");
    final Type photoSizeListType = new TypeToken<List<PhotoSize>>() {}.getType();
    gson.toJson(value.getSizes(), photoSizeListType, out);

    out.endObject();
  }

  @Override
  public Photo read(final JsonReader in) throws IOException {
    final JsonObject jsonObject = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();

    final String caption = jsonObject.has("caption") ? jsonObject.get("caption").getAsString() : null;

    List<PhotoSize> photoSizes = null;
    if (jsonObject.has("alt_sizes")) {
      final Type photoSizeListType = new TypeToken<List<PhotoSize>>() {}.getType();
      photoSizes = gson.fromJson(jsonObject.get("alt_sizes"), photoSizeListType);
    }
    PhotoSize origSize = null;
    if (jsonObject.has("original_size")) {
      final Type origSizeType = new TypeToken<PhotoSize>() {}.getType();
      origSize = gson.fromJson(jsonObject.get("original_size"), origSizeType);
    }

    return new Photo(caption, origSize, photoSizes);
  }
}
