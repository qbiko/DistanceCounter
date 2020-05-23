package chodorowski.jakub.myapplication.api;


import chodorowski.jakub.myapplication.model.RouteResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BingMapsService {

    @GET("Routes/Driving")
    Call<RouteResponse> getDrivingRoute(@Query("waypoint.1") String wayPoint1, @Query("waypoint.2") String wayPoint2,
                                        @Query("distanceUnit") String distanceUnit, @Query("key") String key);
}
