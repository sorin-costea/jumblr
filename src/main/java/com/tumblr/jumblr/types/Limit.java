package com.tumblr.jumblr.types;

public class Limit {
  private String description;
  private Long reset_at;
  private int limit, remaining;

  public String getDescription() {
    return description;
  }

  public Long getReset_at() {
    return reset_at;
  }

  public int getLimit() {
    return limit;
  }

  public int getRemaining() {
    return remaining;
  }

}
