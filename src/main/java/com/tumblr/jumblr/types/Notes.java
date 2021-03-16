package com.tumblr.jumblr.types;

import java.util.List;

public class Notes extends Resource {
  private Long total_notes, total_likes, total_reblogs;
  private List<Note> notes, rollup_notes;
  private Links _links;

  public Long getTotalNotes() {
    return total_notes;
  }

  public Long getTotalLikes() {
    return total_likes;
  }

  public Long getTotalReblogs() {
    return total_reblogs;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public List<Note> getRollupNotes() {
    return rollup_notes;
  }

  public Links getLinks() {
    return _links;
  }
}
