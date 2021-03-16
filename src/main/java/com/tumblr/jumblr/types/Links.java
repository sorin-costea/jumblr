package com.tumblr.jumblr.types;

public class Links extends Resource {
  private Link next, previous;

  public Link getNext() {
    return next;
  }

  public Link getPrevious() {
    return previous;
  }
}
