package org.example;

public class Location {
    private String name;
    private double x;
    private double y;

    public Location(double x,double y,String name){
        this.name=name;
        this.x=x;
        this.y=y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double calculateDistance(double x, double y){
       return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }


}