package com.tumblr.jumblr.types;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tumblr.jumblr.JumblrClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Blog tests
 *
 * @author jc
 */
public class BlogTest extends TypeTest {

  JumblrClient client;
  Blog blog;

  private final String name = "name.com", title = "title", description = "desc";
  private final Integer posts = 10, likes = 11, followers = 10;
  private final Long updated = 123456L;
  private final boolean ask = false, ask_anon = true;

  @Before
  public void setup() throws IOException {
    final Map<String, Object> flat = new HashMap<String, Object>();
    flat.put("name", name);
    flat.put("title", title);
    flat.put("description", description);
    flat.put("posts", posts);
    flat.put("likes", likes);
    flat.put("updated", updated);
    flat.put("ask", ask);
    flat.put("ask_anon", ask_anon);
    flat.put("followers", followers);

    final Gson gson = new Gson();
    blog = gson.fromJson(flatSerialize(flat), Blog.class);
    blog.setName(blog.getName());
    client = mock(JumblrClient.class);
    blog.setClient(client);
  }

  @Test
  public void newPost() throws IllegalAccessException, InstantiationException {
    blog.newPost(QuotePost.class);
    verify(client).newPost(name, QuotePost.class);
  }

  @Test
  public void testReaders() {
    assertEquals(description, blog.getDescription());
    assertEquals(ask, blog.canAsk());
    assertEquals(ask_anon, blog.canAskAnonymously());
    assertEquals(posts, blog.getPostCount());
    assertEquals(likes, blog.getLikeCount());
    assertEquals(updated, blog.getUpdated());
    assertEquals(title, blog.getTitle());
    assertEquals(name, blog.getName());
    assertEquals(followers, blog.getFollowersCount());
  }

  @Test
  public void avatar() {
    blog.avatar();
    verify(client).blogAvatar(name, null);

    blog.avatar(96);
    verify(client).blogAvatar(name, 96);
  }

  @Test
  public void followers() {
    blog.followers();
    verify(client).blogFollowers(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.followers(options);
    verify(client).blogFollowers(name, options);
  }

  @Test
  public void posts() {
    blog.posts();
    verify(client).blogPosts(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.posts(options);
    verify(client).blogPosts(name, options);
  }

  @Test
  public void getPost() {
    blog.getPost(42L);
    verify(client).blogPost(name, 42L);
  }

  @Test
  public void getLikedPosts() {
    blog.likedPosts();
    verify(client).blogLikes(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.likedPosts(options);
    verify(client).blogLikes(name, options);
  }

  @Test
  public void getFollow() {
    blog.follow();
    verify(client).follow(name);
  }

  @Test
  public void getUnfollow() {
    blog.unfollow();
    verify(client).unfollow(name);
  }

  @Test
  public void queuedPosts() {
    blog.queuedPosts();
    verify(client).blogQueuedPosts(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.queuedPosts(options);
    verify(client).blogQueuedPosts(name, options);
  }

  @Test
  public void draftPosts() {
    blog.draftPosts();
    verify(client).blogDraftPosts(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.draftPosts(options);
    verify(client).blogDraftPosts(name, options);
  }

  @Test
  public void submissions() {
    blog.submissions();
    verify(client).blogSubmissions(name, null);

    final Map<String, String> options = new HashMap<String, String>();
    blog.submissions(options);
    verify(client).blogSubmissions(name, options);
  }

  @Test
  public void notifications() {
    final Map<String, String> options = new HashMap<String, String>();
    blog.notifications(options);
    verify(client).blogNotifications(name, options);
  }

  @Test
  public void postNotes() {
    final Map<String, String> options = new HashMap<String, String>();
    final Long id = 42L;
    blog.postNotes(id, options);
    verify(client).blogPostNotes(name, id, options);
  }

}
