package com.tumblr.jumblr.types;

import java.util.Map;

public class UserLimits extends Resource {
  private Map<String, Limit> user;

  public Map<String, Limit> getUser() {
    return user;
  }
}
