package com.anglernation.models.lures;

import com.anglernation.models.AbstractEntity;



public class Lure extends AbstractEntity {

    private LureType lureType;
    private String name;
    private String color;
    private Double size;
    private Double weight;
    private LureBuoyancy lureBuoyancy;

    public Lure(LureType lureType, String name, String color, Double size, Double weight, LureBuoyancy lureBuoyancy) {
        this.lureType = lureType;
        this.name = name;
        this.color = color;
        this.size = size;
        this.weight = weight;
        this.lureBuoyancy = lureBuoyancy;
    }

    public LureType getLureType() {
        return lureType;
    }

    public void setLureType(LureType lureType) {
        this.lureType = lureType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LureBuoyancy getLureBuoyancy() {
        return lureBuoyancy;
    }

    public void setLureBuoyancy(LureBuoyancy lureBuoyancy) {
        this.lureBuoyancy = lureBuoyancy;
    }

    @Override
    public String toString() {
        return "Lure{" +
                "lureType=" + lureType +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                ", lureBuoyancy=" + lureBuoyancy +
                '}';
    }
}
