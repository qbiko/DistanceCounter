package chodorowski.jakub.myapplication;

import org.junit.Before;
import org.junit.Test;

import chodorowski.jakub.myapplication.model.Resource;
import chodorowski.jakub.myapplication.model.ResourceSet;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ResourceSetUnitTest {

    private ResourceSet mResourceSet;
    private final double DISTANCE_KM = 999.99;

    @Before
    public void setUp() {
        mResourceSet = new ResourceSet();
    }

    @Test
    public void whenResourcesAreNullThenGetTravelDistanceIsNull() {
        assertThat(mResourceSet.getTravelDistance(), is(nullValue()));
    }

    @Test
    public void whenResourcesAreNullThenGetTravelDistanceInMetresIsNull() {
        assertThat(mResourceSet.getTravelDistance(), is(nullValue()));
    }

    @Test
    public void whenResourceAreInitializedGetTravelDistanceReturnCorrectValue() {
        Resource resource = new Resource();
        resource.setTravelDistance(DISTANCE_KM);
        mResourceSet.setResources(new Resource[] { resource });
        assertThat(mResourceSet.getTravelDistance(), is(DISTANCE_KM));
    }

    @Test
    public void whenResourceAreInitializedGetTravelDistanceInMetresReturnCorrectValue() {
        Resource resource = new Resource();
        resource.setTravelDistance(DISTANCE_KM);
        mResourceSet.setResources(new Resource[] { resource });
        assertThat(mResourceSet.getTravelDistanceInMetres(), is(DISTANCE_KM*1000));
    }
}