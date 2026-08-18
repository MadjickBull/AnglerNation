package com.anglernation.models.posts;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.catches.Catch;

import java.util.ArrayList;
import java.util.List;

public class Post extends AbstractEntity {

  private String title;
  private String description;
  private List<String> comments = new ArrayList<>();
  private List<Catch> catchDetails = new ArrayList<>();

  public Post(String title, String description, List<String> comments, List<Catch> catchDetails) {
    this.title = title;
    this.description = description;
    this.comments = comments;
    this.catchDetails = catchDetails;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }

  public List<Catch> getCatchDetails() {
    return catchDetails;
  }

  public void setCatchDetails(List<Catch> catchDetails) {
    this.catchDetails = catchDetails;
  }

  @Override
  public String toString() {
    return "Post{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", comments=" + comments +
        ", catchDetails=" + catchDetails +
        '}';
  }
}
