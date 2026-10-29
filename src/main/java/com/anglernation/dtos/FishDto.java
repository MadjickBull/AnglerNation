package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.Fish.FishType;
import java.util.UUID;

@Schema(description = "Fish DTO with front-end payload")
public class FishDto {

    @Schema(
            description = "Unique identifier for the fish",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Type of fish (BASS, TROUT, Pike, etc.)",
            example = "Bass",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private FishType fishType;

    @Schema(
            description = "Name/common name of the fish",
            example = "Largemouth Bass",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Weight of the fish in pounds",
            example = "4.5"
    )
    private Double weight;

    @Schema(
            description = "Size measurement of the fish",
            example = "22.5"
    )
    private Double size;

    public FishDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}