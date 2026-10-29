package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.locations.LocationVisibility;
import java.util.UUID;

@Schema(description = "Location DTO with front-end payload")
public class LocationDto {

    @Schema(
            description = "Unique identifier for the location",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Name of the location (lake, river, coast)",
            example = "Lake Powell",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Visibility status of the location (PUBLIC, PRIVATE)",
            example = "PUBLIC"
    )
    private LocationVisibility visibility;

    @Schema(
            description = "Altitude of the location in meters",
            example = "1250.0"
    )
    private Double altitude;

    @Schema(
            description = "Longitude coordinate",
            example = "-111.5"
    )
    private Double longitude;

    @Schema(
            description = "Latitude coordinate",
            example = "36.8"
    )
    private Double latitude;

    public LocationDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(LocationVisibility visibility) {
        this.visibility = visibility;
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
}