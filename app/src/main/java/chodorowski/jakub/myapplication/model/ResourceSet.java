package chodorowski.jakub.myapplication.model;

public class ResourceSet {

    private Resource[] resources;

    public Double getTravelDistance() {
        if(resources != null && resources.length > 0) {
            return resources[0].getTravelDistance();
        }
        return null;
    }

    public Double getTravelDistanceInMetres() {
        if(resources != null && resources.length > 0) {
            return resources[0].getTravelDistanceInMetres();
        }
        return null;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }
}
