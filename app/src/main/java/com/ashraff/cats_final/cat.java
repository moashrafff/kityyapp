package com.ashraff.cats_final;

public class cat {

    private int id ;
    private String name ;
    private String color;
    private double weight;
    private String eyeColor;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public cat(int id, String name, String color, double weight, String eyeColor, String image) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.image = image;
    }

    public cat(String name, String color, double weight, String eyeColor, String image) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.image = image;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
