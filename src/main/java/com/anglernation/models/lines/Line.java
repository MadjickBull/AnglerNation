package com.anglernation.models.lines;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "lines")
public class Line extends AbstractEntity {

  @Enumerated(EnumType.STRING)
  private LineType lineType;
  @Column(name = "brand")
  private String name;
  @Column(name = "diameter")
  private Double diameter;
  @Column(name = "breaking_strength")
  private Integer breakingStrength;
  @Column(name = "color")
  private String color;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  public Line(){
  };

  public LineType getLineType() {
    return lineType;
  }

  public void setLineType(LineType lineType) {
    this.lineType = lineType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getDiameter() {
    return diameter;
  }

  public void setDiameter(Double diameter) {
    this.diameter = diameter;
  }

  public Integer getBreakingStrength() {
    return breakingStrength;
  }

  public void setBreakingStrength(Integer breakingStrength) {
    this.breakingStrength = breakingStrength;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
