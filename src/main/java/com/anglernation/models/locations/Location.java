package com.anglernation.models.locations;

public class Location {

    private String name;
    private Long altitude;
    private Long longitude;
    private Long latitude;
    private Double altitudeRadius;
    private Double longitudeRadius;
    private Double latitudeRadius;

    public Location(String name, Long altitude, Long longitude, Long latitude, Double altitudeRadius, Double longitudeRadius, Double latitudeRadius) {
        this.name = name;
        this.altitude = altitude;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitudeRadius = altitudeRadius;
        this.longitudeRadius = longitudeRadius;
        this.latitudeRadius = latitudeRadius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAltitude() {
        return altitude;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Double getAltitudeRadius() {
        return altitudeRadius;
    }

    public void setAltitudeRadius(Double altitudeRadius) {
        this.altitudeRadius = altitudeRadius;
    }

    public Double getLongitudeRadius() {
        return longitudeRadius;
    }

    public void setLongitudeRadius(Double longitudeRadius) {
        this.longitudeRadius = longitudeRadius;
    }

    public Double getLatitudeRadius() {
        return latitudeRadius;
    }

    public void setLatitudeRadius(Double latitudeRadius) {
        this.latitudeRadius = latitudeRadius;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", altitude=" + altitude +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitudeRadius=" + altitudeRadius +
                ", longitudeRadius=" + longitudeRadius +
                ", latitudeRadius=" + latitudeRadius +
                '}';
    }
}
