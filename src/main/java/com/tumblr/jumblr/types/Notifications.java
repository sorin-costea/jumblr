package com.tumblr.jumblr.types;

import java.util.List;

public class Notifications extends Resource {
  private List<Notification> notifications;
  private Links _links;

  public List<Notification> getNotifications() {
    return notifications;
  }

  public Links getLinks() {
    return _links;
  }
}
