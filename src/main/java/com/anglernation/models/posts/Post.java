package com.anglernation.models.posts;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.comments.Comment;
import com.anglernation.models.users.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends AbstractEntity {

  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "description")
  private String description;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;
  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true , cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  public Post() {
  }

  public User getAuthor() {

    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
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

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

}
