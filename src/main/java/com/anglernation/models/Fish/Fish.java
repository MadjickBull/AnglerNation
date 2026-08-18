package com.anglernation.models.Fish;

import com.anglernation.models.AbstractEntity;



public class Fish extends AbstractEntity {


    private FishType fishType;
    private String name;
    private Double weight;
    private Double size;

    public Fish(FishType fishType, String name, Double weight, Double size) {
        this.fishType = fishType;
        this.name = name;
        this.weight = weight;
        this.size = size;
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

    @Override
    public String toString() {
        return "Fish{" +
                "fishType=" + fishType +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", size=" + size +
                '}';
    }
}
