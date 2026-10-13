package com.anglernation.models.locations;

import com.anglernation.models.AbstractEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location extends AbstractEntity {

  @Column(name = "location_name" , nullable = true)
  private String name;
  @Enumerated(EnumType.STRING)
  @Column(name = "visibility" , nullable = true)
  private LocationVisibility visibility;
  @Column(name = "altitude" , nullable = true)
  private Double altitude;
  @Column(name = "longitude", nullable = true)
  private Double longitude;
  @Column(name = "latitude" , nullable = true)
  private Double latitude;

  public Location() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getAltitude() {
    return altitude;
  }

  public void setAltitude(Double altitude) {
    this.altitude = altitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public LocationVisibility getVisibility() {
    return visibility;
  }

  public void setVisibility(LocationVisibility visibility) {
    this.visibility = visibility;
  }
}