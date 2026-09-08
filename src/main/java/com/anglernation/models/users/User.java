package com.anglernation.models.users;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.catches.Catch;
import com.anglernation.models.lines.Line;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.posts.Post;
import com.anglernation.models.rods.Rod;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

  @Column(name = "user_name", nullable = false, unique = true)
  private String userName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rod> rods = new ArrayList<>();

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Lure> lures = new ArrayList<>();

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Line> lines = new ArrayList<>();

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "angler", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Catch> catches = new ArrayList<>();

  public User(){
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Rod> getRods() {
    return rods;
  }

  public void setRods(List<Rod> rods) {
    this.rods = rods;
  }

  public List<Lure> getLure() {
    return lures;
  }

  public void setLure(List<Lure> lure) {
    this.lures = lure;
  }

  public List<Line> getLines() {
    return lines;
  }

  public void setLines(List<Line> lines) {
    this.lines = lines;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public List<Catch> getCatches() {
    return catches;
  }

  public void setCatches(List<Catch> catches) {
    this.catches = catches;
  }

}
