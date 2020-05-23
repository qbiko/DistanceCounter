package chodorowski.jakub.myapplication.model;

public class RouteResponse {

    ResourceSet[] resourceSets;

    public Double getTravelDistance() {
        if(resourceSets != null && resourceSets.length > 0) {
            return resourceSets[0].getTravelDistance();
        }
        return null;
    }

    public Double getTravelDistanceInMetres() {
        if(resourceSets != null && resourceSets.length > 0) {
            return resourceSets[0].getTravelDistanceInMetres();
        }
        return null;
    }

    public void setResourceSets(ResourceSet[] resourceSets) {
        this.resourceSets = resourceSets;
    }
}
