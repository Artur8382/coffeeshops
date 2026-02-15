package org.example;

public class Location {
    private String name;
    private double x;
    private double y;
    private double distance;

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

    public double getDistance() {
        return distance;
    }

    public double calcualteDistance(double x, double y){
        this.distance=Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        return this.distance;
    }


}