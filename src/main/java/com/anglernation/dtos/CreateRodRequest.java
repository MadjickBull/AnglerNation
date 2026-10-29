package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anglernation.models.rods.RodType;
import com.anglernation.models.rods.RodPower;
import com.anglernation.models.rods.RodAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Create rod request DTO for front-end API consumption")
public class CreateRodRequest {

    @Schema(
            description = "Type of rod (SPIN, CAST, TROLL, etc.)",
            example = "SPIN",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private RodType rodType;

    @Schema(
            description = "Name/model of the rod",
            example = "Fast Action Casting Rod",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Length of the rod in feet",
            example = "7.5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Positive
    private Double length;

    @Schema(
            description = "Weight of the rod in ounces",
            example = "2.3"
    )
    @PositiveOrZero
    private Double weight;

    @Schema(
            description = "Power rating of the rod (LIGHT, MEDIUM, HEAVY)",
            example = "MEDIUM",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private RodPower rodPower;

    @Schema(
            description = "Action type of the rod (FAST, MODERATE, SLOW)",
            example = "FAST",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private RodAction rodAction;

    public CreateRodRequest() {}

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