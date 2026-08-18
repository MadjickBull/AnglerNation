package com.anglernation.models.users;

import com.anglernation.models.catches.Catch;
import com.anglernation.models.lines.Line;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.posts.Post;
import com.anglernation.models.rods.Rod;

import java.util.ArrayList;
import java.util.List;

public class User {

  private String userName;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private Integer phoneNumber;

  private List<Rod> rods = new ArrayList<>();
  private List<Lure> lure = new ArrayList<>();
  private List<Line> lines = new ArrayList<>();
  private List<Post> posts = new ArrayList<>();
  private List<Catch> catches = new ArrayList<>();

  public User(String userName, String firstName, String lastName, String password, String email, Integer phoneNumber,
      List<Rod> rods, List<Lure> lure, List<Line> lines, List<Post> posts, List<Catch> catches) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.rods = rods;
    this.lure = lure;
    this.lines = lines;
    this.posts = posts;
    this.catches = catches;
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

  public Integer getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Integer phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Rod> getRods() {
    return rods;
  }

  public void setRods(List<Rod> rods) {
    this.rods = rods;
  }

  public List<Lure> getLure() {
    return lure;
  }

  public void setLure(List<Lure> lure) {
    this.lure = lure;
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

  @Override
  public String toString() {
    return "User{" +
        "userName='" + userName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber=" + phoneNumber +
        ", rods=" + rods +
        ", lure=" + lure +
        ", lines=" + lines +
        ", posts=" + posts +
        ", catches=" + catches +
        '}';
  }
}
