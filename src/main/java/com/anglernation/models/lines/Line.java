package com.anglernation.models.lines;

public class Line {

    private LineType lineType;
    private String name;
    private Double diameter;
    private Integer breakingStrength;
    private String color;

    public Line(LineType lineType, String name, Double diameter, Integer breakingStrength, String color) {
        this.lineType = lineType;
        this.name = name;
        this.diameter = diameter;
        this.breakingStrength = breakingStrength;
        this.color = color;
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

    @Override
    public String toString() {
        return "Line{" +
                "lineType=" + lineType +
                ", name='" + name + '\'' +
                ", diameter=" + diameter +
                ", breakingStrength=" + breakingStrength +
                ", Color='" + color + '\'' +
                '}';
    }
}

