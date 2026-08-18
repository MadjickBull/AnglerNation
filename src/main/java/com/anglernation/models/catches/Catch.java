package com.anglernation.models.catches;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.Fish.Fish;
import com.anglernation.models.lines.Line;
import com.anglernation.models.locations.Location;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.posts.Post;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.users.User;

public class Catch extends AbstractEntity {

  private Location location;
  private Rod rod;
  private Lure lure;
  private Line line;
  private Fish fish;
  private Post post;
  private User angler;

  public Catch(Location location, Rod rod, Lure lure, Line line, Fish fish, Post post, User angler) {
    this.location = location;
    this.rod = rod;
    this.lure = lure;
    this.line = line;
    this.fish = fish;
    this.post = post;
    this.angler = angler;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Rod getRod() {
    return rod;
  }

  public void setRod(Rod rod) {
    this.rod = rod;
  }

  public Lure getLure() {
    return lure;
  }

  public void setLure(Lure lure) {
    this.lure = lure;
  }

  public Line getLine() {
    return line;
  }

  public void setLine(Line line) {
    this.line = line;
  }

  public Fish getFish() {
    return fish;
  }

  public void setFish(Fish fish) {
    this.fish = fish;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public User getAngler() {
    return angler;
  }

  public void setAngler(User angler) {
    this.angler = angler;
  }

  @Override
  public String toString() {
    return "Catch{" +
        "location=" + location +
        ", rod=" + rod +
        ", lure=" + lure +
        ", line=" + line +
        ", fish=" + fish +
        ", post=" + post +
        ", angler=" + angler +
        '}';
  }
}
