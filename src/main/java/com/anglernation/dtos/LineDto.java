package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.lines.LineType;
import java.util.UUID;

@Schema(description = "Line DTO with front-end payload")
public class LineDto {

    @Schema(
            description = "Unique identifier for the line",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Type of fishing line (BRAIDED, MONOFILAMENT, FLUOROCARBON)",
            example = "BRAIDED"
    )
    private LineType lineType;

    @Schema(
            description = "Name/model of the line",
            example = "X-Tra Strong Line"
    )
    private String name;

    @Schema(
            description = "Diameter of the line in millimeters",
            example = "0.35"
    )
    private Double diameter;

    @Schema(
            description = "Breaking strength in pounds",
            example = "30"
    )
    private Integer breakingStrength;

    @Schema(
            description = "Color of the line",
            example = "Green"
    )
    private String color;

    @Schema(
            description = "Owner user ID",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID ownerId;

    public LineDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}