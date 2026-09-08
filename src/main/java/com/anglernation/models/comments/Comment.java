package com.anglernation.models.comments;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.posts.Post;
import com.anglernation.models.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity {

  @Column(nullable = false)
  private String content;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  public Comment() {
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }
}
