package com.anglernation.models.rods;

public class Rod {

    private RodType rodType;
    private String name;
    private Double length;
    private Double weight;
    private RodPower rodPower;
    private RodAction rodAction;

    public Rod(RodType rodType, String name, Double length, Double weight, RodPower rodPower, RodAction rodAction) {
        this.rodType = rodType;
        this.name = name;
        this.length = length;
        this.weight = weight;
        this.rodPower = rodPower;
        this.rodAction = rodAction;
    }

    public RodType getRodType(){
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

    @Override
    public String toString() {
        return "Rod{" +
                "rodType=" + rodType +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", weight=" + weight +
                ", rodPower=" + rodPower +
                ", rodAction=" + rodAction +
                '}';
    }
}
