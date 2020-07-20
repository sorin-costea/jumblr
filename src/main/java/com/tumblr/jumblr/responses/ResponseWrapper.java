package com.tumblr.jumblr.responses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.Resource;
import com.tumblr.jumblr.types.User;

import java.util.List;

public class ResponseWrapper {

  private JsonElement response;
  private JumblrClient client;

  public void setClient(final JumblrClient client) {
    this.client = client;
  }

  public User getUser() {
    return get("user", User.class);
  }

  public Blog getBlog() {
    return get("blog", Blog.class);
  }

  public Post getPost() {
    return get("post", Post.class);
  }

  public Long getId() {
    final JsonObject object = (JsonObject) response;
    return object.get("id").getAsLong();
  }

  public boolean getFollowedBy() {
    final JsonObject object = (JsonObject) response;
    return object.get("followed_by").getAsBoolean();
  }

  // NOTE: needs to be duplicated logic due to Java erasure of generic types
  public List<Post> getPosts() {
    final Gson gson = gsonParser();
    final JsonObject object = (JsonObject) response;
    final List<Post> l = gson.fromJson(object.get("posts"), new TypeToken<List<Post>>() {}.getType());
    for (final Post e : l) {
      e.setClient(client);
    }
    return l;
  }

  // NOTE: needs to be duplicated logic due to Java erasure of generic types
  public List<User> getUsers() {
    final Gson gson = gsonParser();
    final JsonObject object = (JsonObject) response;
    final List<User> l = gson.fromJson(object.get("users"), new TypeToken<List<User>>() {}.getType());
    for (final User e : l) {
      e.setClient(client);
    }
    return l;
  }

  // NOTE: needs to be duplicated logic due to Java erasure of generic types
  public List<Post> getLikedPosts() {
    final Gson gson = gsonParser();
    final JsonObject object = (JsonObject) response;
    final List<Post> l = gson.fromJson(object.get("liked_posts"), new TypeToken<List<Post>>() {}.getType());
    for (final Post e : l) {
      e.setClient(client);
    }
    return l;
  }

  // NOTE: needs to be duplicated logic due to Java erasure of generic types
  public List<Post> getTaggedPosts() {
    final Gson gson = gsonParser();
    final List<Post> l = gson.fromJson(response.getAsJsonArray(), new TypeToken<List<Post>>() {}.getType());
    for (final Post e : l) {
      e.setClient(client);
    }
    return l;
  }

  // NOTE: needs to be duplicated logic due to Java erasure of generic types
  public List<Blog> getBlogs() {
    final Gson gson = gsonParser();
    final JsonObject object = (JsonObject) response;
    final List<Blog> l = gson.fromJson(object.get("blogs"), new TypeToken<List<Blog>>() {}.getType());
    for (final Blog e : l) {
      e.setClient(client);
    }
    return l;
  }

  /**
   **
   **/

  private <T extends Resource> T get(final String field, final Class<T> k) {
    final Gson gson = gsonParser();
    final JsonObject object = (JsonObject) response;
    final T e = gson.fromJson(object.get(field).toString(), k);
    e.setClient(client);
    return e;
  }

  private Gson gsonParser() {
    return new GsonBuilder().registerTypeAdapter(Post.class, new PostDeserializer()).create();
  }

}
