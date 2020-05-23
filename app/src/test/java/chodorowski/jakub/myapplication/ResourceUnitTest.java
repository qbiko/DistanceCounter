package chodorowski.jakub.myapplication;

import org.junit.Before;
import org.junit.Test;

import chodorowski.jakub.myapplication.model.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResourceUnitTest {

    private Resource mResource;

    private final double DISTANCE_KM = 999.99;

    @Before
    public void setUp() {
        mResource = new Resource();
        mResource.setTravelDistance(DISTANCE_KM);
    }

    @Test
    public void whenDistanceInKmIs999Point99ThenDistanceInMIs9999900() {
        double distanceInM = DISTANCE_KM * 1000;
        assertThat(mResource.getTravelDistanceInMetres(), is(distanceInM));
    }
}
