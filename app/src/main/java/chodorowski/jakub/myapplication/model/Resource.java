package chodorowski.jakub.myapplication.model;

public class Resource {

    private double travelDistance;

    public double getTravelDistance() {
        return travelDistance;
    }

    public double getTravelDistanceInMetres() {
        return travelDistance * 1000;
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }
}
