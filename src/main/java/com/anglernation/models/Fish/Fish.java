package com.anglernation.models.Fish;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.catches.Catch;
import jakarta.persistence.*;

@Entity
@Table( name = "fish")
public class Fish extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private FishType fishType;
    @Column(name = "species")
    private String name;
    @Column(name = "weight")
    private Double weight;
    @Column(name = "size")
    private Double size;

    public Fish(){
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
