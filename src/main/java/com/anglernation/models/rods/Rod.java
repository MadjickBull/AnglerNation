package com.anglernation.models.rods;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.users.User;
import jakarta.persistence.*;

@Entity
@Table(name="rods")
public class Rod extends AbstractEntity {

  @Enumerated(EnumType.STRING)
  private RodType rodType;
  @Column(name="brand")
  private String name;
  @Column(name="length")
  private Double length;
  @Column(name = "weight")
  private Double weight;
  @Enumerated(EnumType.STRING)
  private RodPower rodPower;
  @Enumerated(EnumType.STRING)
  private RodAction rodAction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  public Rod (){};

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public RodType getRodType() {
    return rodType;
  }

  public void setRodType(RodType rodType) {
    this.rodType = rodType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLength() {
    return length;
  }

  public void setLength(Double length) {
    this.length = length;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public RodPower getRodPower() {
    return rodPower;
  }

  public void setRodPower(RodPower rodPower) {
    this.rodPower = rodPower;
  }

  public RodAction getRodAction() {
    return rodAction;
  }

  public void setRodAction(RodAction rodAction) {
    this.rodAction = rodAction;
  }

}
