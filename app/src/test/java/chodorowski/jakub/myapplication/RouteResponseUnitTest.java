package chodorowski.jakub.myapplication;

import org.junit.Before;
import org.junit.Test;

import chodorowski.jakub.myapplication.model.Resource;
import chodorowski.jakub.myapplication.model.ResourceSet;
import chodorowski.jakub.myapplication.model.RouteResponse;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class RouteResponseUnitTest {

    private RouteResponse mRouteResponse;
    private final double DISTANCE_KM = 999.99;

    @Before
    public void setUp() {
        mRouteResponse = new RouteResponse();
    }

    @Test
    public void whenResourcesSetsAreNullThenGetTravelDistanceIsNull() {
        assertThat(mRouteResponse.getTravelDistance(), is(nullValue()));
    }

    @Test
    public void whenResourcesSetsAreNullThenGetTravelDistanceInMetresIsNull() {
        assertThat(mRouteResponse.getTravelDistanceInMetres(), is(nullValue()));
    }

    @Test
    public void whenResourcesSetsAreAreInitializedThenGetTravelDistanceIsNull() {
        Resource resource = new Resource();
        resource.setTravelDistance(DISTANCE_KM);
        ResourceSet resourceSet = new ResourceSet();
        resourceSet.setResources(new Resource[] { resource });
        mRouteResponse.setResourceSets(new ResourceSet[] {resourceSet});
        assertThat(mRouteResponse.getTravelDistance(), is(DISTANCE_KM));
    }

    @Test
    public void whenResourcesSetsAreAreInitializedThenGetTravelDistanceInMetresIsNull() {
        Resource resource = new Resource();
        resource.setTravelDistance(DISTANCE_KM);
        ResourceSet resourceSet = new ResourceSet();
        resourceSet.setResources(new Resource[] { resource });
        mRouteResponse.setResourceSets(new ResourceSet[] {resourceSet});
        assertThat(mRouteResponse.getTravelDistanceInMetres(), is(DISTANCE_KM*1000));
    }
}
