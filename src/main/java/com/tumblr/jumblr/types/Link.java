package com.tumblr.jumblr.types;

public class Link extends Resource {
  private String type, href, method;
  private QueryParams query_params;

  public String getType() {
    return type;
  }

  public String getHref() {
    return href;
  }

  public String getMethod() {
    return method;
  }

  public QueryParams getQueryParams() {
    return query_params;
  }

}
