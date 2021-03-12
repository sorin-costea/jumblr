package com.tumblr.jumblr.responses;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Gson is designed to crash when tasked to deserialize an empty numeric field. Thus we deserialize that to null. We
 * only have here the problem for Long fields, but it culd happen anywhere.
 *
 */
public class LongTypeAdapter extends TypeAdapter<Number> {

  @Override
  public void write(final JsonWriter out, final Number value) throws IOException {
    out.value(value);
  }

  @Override
  public Number read(final JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }
    try {
      final String result = in.nextString();
      if ("".equals(result)) {
        return null;
      }
      return Long.parseLong(result);
    } catch (final NumberFormatException e) {
      throw new JsonSyntaxException(e);
    }
  }

}
