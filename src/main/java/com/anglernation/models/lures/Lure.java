package com.anglernation.models.lures;

import com.anglernation.models.AbstractEntity;
import com.anglernation.models.users.User;
import jakarta.persistence.*;


@Entity
@Table(name = "lures")
public class Lure extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private LureType lureType;
    @Column(name = "brand")
    private String name;
    @Column(name = "color")
    private String color;
    @Column(name = "size")
    private Double size;
    @Column(name = "weight")
    private Double weight;
    @Enumerated(EnumType.STRING)
    private LureBuoyancy lureBuoyancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id" , nullable = false)
    private User owner;

    public Lure(){
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

}
