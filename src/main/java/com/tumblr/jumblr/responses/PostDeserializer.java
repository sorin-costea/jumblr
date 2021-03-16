package com.tumblr.jumblr.responses;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tumblr.jumblr.types.UnknownTypePost;

import java.lang.reflect.Type;

/**
 * Posts come back to us as a collection, so this Deserializer is here to make it so that the collection consists of the
 * proper subclasses of Post (ie: QuotePost, PhotoPost)
 *
 * @author jc
 */
public class PostDeserializer implements JsonDeserializer<Object> {

  @Override
  public Object deserialize(final JsonElement je, @SuppressWarnings("unused") final Type type,
      final JsonDeserializationContext jdc) throws JsonParseException {
    final JsonObject jobject = je.getAsJsonObject();
    if (jobject.get("type") == null) {
      return jdc.deserialize(je, UnknownTypePost.class); // it's a trail where only the post ID is valid
    }

    final String typeName = jobject.get("type").getAsString();
    final String className = typeName.substring(0, 1).toUpperCase() + typeName.substring(1) + "Post";
    try {
      final Class<?> clz = Class.forName("com.tumblr.jumblr.types." + className);
      return jdc.deserialize(je, clz);
    } catch (final ClassNotFoundException e) {
      System.out.println("deserialized post for unknown type: " + typeName);
      return jdc.deserialize(je, UnknownTypePost.class);
    }
  }

}
