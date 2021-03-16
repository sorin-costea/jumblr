package com.tumblr.jumblr.types;

public class QueryParams {
  private Long page;
  private String before;
  private String[] types;

  public Long getPage() {
    return page;
  }

  public String getBefore() {
    return before;
  }

  public String[] getTypes() {
    return types;
  }
}
