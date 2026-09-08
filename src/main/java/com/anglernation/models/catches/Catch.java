package com.anglernation.models.catches;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.Fish.Fish;
import com.anglernation.models.lines.Line;
import com.anglernation.models.locations.Location;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.posts.Post;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.users.User;
import jakarta.persistence.*;

@Entity
@Table( name = "catches")
public class Catch extends AbstractEntity {

  @ManyToOne
  @JoinColumn( name = "location_id" , nullable = false)
  private Location location;
  @JoinColumn(name = "rod_id" , nullable = true)
  @ManyToOne
  private Rod rod;
  @JoinColumn(name = "lure_id", nullable = true)
  @ManyToOne
  private Lure lure;
  @JoinColumn(name = "line_id" , nullable = true)
  @ManyToOne
  private Line line;
  @JoinColumn(name = "fish_id" , nullable = false)
  @ManyToOne
  private Fish fish;
  @JoinColumn(name = "angler_id" ,  nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private User angler;

  public Catch(){
  };

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


  public User getAngler() {
    return angler;
  }

  public void setAngler(User angler) {
    this.angler = angler;
  }


}
