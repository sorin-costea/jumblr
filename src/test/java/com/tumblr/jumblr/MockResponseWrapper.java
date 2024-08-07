package com.tumblr.jumblr;

import java.util.ArrayList;
import java.util.List;

import com.tumblr.jumblr.responses.ResponseWrapper;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Follower;
import com.tumblr.jumblr.types.Notes;
import com.tumblr.jumblr.types.Notifications;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;
import com.tumblr.jumblr.types.UserLimits;

/**
 *
 * @author jc
 */
public class MockResponseWrapper extends ResponseWrapper {

  @Override
  public List<Post> getPosts() {
    return new ArrayList<Post>();
  }

  @Override
  public Notifications getNotifications() {
    return new Notifications();
  }

  @Override
  public Notes getNotes() {
    return new Notes();
  }

  @Override
  public List<User> getUsers() {
    return new ArrayList<User>();
  }

  @Override
  public List<Follower> getFollowers() {
    return new ArrayList<Follower>();
  }

  @Override
  public List<Post> getTaggedPosts() {
    return new ArrayList<Post>();
  }

  @Override
  public List<Post> getLikedPosts() {
    return new ArrayList<Post>();
  }

  @Override
  public List<Blog> getBlogs() {
    return new ArrayList<Blog>();
  }

  @Override
  public Post getPost() {
    return null;
  }

  @Override
  public Blog getBlog() {
    return null;
  }

  @Override
  public User getUser() {
    return null;
  }

  @Override
  public Long getId() {
    return 42L;
  }

  @Override
  public boolean getFollowedBy() {
    return true;
  }

  @Override
  public UserLimits getUserLimits() {
    return new UserLimits();
  }

}
