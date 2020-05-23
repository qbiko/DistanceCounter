package chodorowski.jakub.myapplication.controllers;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.microsoft.maps.Geopath;
import com.microsoft.maps.Geopoint;
import com.microsoft.maps.Geoposition;
import com.microsoft.maps.MapAnimationKind;
import com.microsoft.maps.MapElementLayer;
import com.microsoft.maps.MapIcon;
import com.microsoft.maps.MapPolyline;
import com.microsoft.maps.MapScene;
import com.microsoft.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import chodorowski.jakub.myapplication.api.BingMapsClient;
import chodorowski.jakub.myapplication.api.BingMapsService;
import chodorowski.jakub.myapplication.BuildConfig;
import chodorowski.jakub.myapplication.R;
import chodorowski.jakub.myapplication.model.RouteResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.microsoft.maps.MapRenderMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment {

    @BindView(R.id.map_view)
    FrameLayout mMapViewContainer;
    @BindView(R.id.kilometresTextView)
    TextView mKilometresTextView;
    @BindView(R.id.metresPointTextView)
    TextView mMetresPointTextView;
    @BindView(R.id.topDescTextView)
    TextView mTopDescTextView;

    private MapView mMapView;
    private RouteResponse mRouteResponse;
    private Double mDistanceInKm;
    private Double mDistanceInM;

    private final String DISTANCE_UNIT =  "km";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, v);

        mMapView = new MapView(Objects.requireNonNull(getActivity()), MapRenderMode.RASTER);
        mMapView.setCredentialsKey(BuildConfig.CREDENTIALS_KEY);

        double firstLat = getActivity().getIntent().getDoubleExtra("firstLat", 0);
        double firstLong = getActivity().getIntent().getDoubleExtra("firstLong", 0);
        double secondLat = getActivity().getIntent().getDoubleExtra("secondLat", 0);
        double secondLong = getActivity().getIntent().getDoubleExtra("secondLong", 0);

        Geoposition firstGeoposition = new Geoposition(firstLat, firstLong);
        Geoposition secondGeoposition = new Geoposition(secondLat, secondLong);

        if(savedInstanceState != null) {
            mMapView.onCreate(savedInstanceState);
            mMapViewContainer.addView(mMapView);

            mDistanceInKm = savedInstanceState.getDouble("distanceInKm");
            mDistanceInM = savedInstanceState.getDouble("distanceInM");

            if(mDistanceInKm != null) {
                mKilometresTextView.setText(getString(R.string.distance_km, mDistanceInKm));
            }
            if(mDistanceInM != null) {
                mMetresPointTextView.setText(getString(R.string.distance_m, mDistanceInM));
            }
        }
        else {
            mMapViewContainer.addView(mMapView);

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();

            BingMapsService service = BingMapsClient.getRetrofitInstance().create(BingMapsService.class);
            Call<RouteResponse> call = service.getDrivingRoute(geoPositionToString(firstGeoposition), geoPositionToString(secondGeoposition),
                    DISTANCE_UNIT, BuildConfig.CREDENTIALS_KEY);
            call.enqueue(new Callback<RouteResponse>() {
                @Override
                public void onResponse(@NonNull Call<RouteResponse> call, @NonNull Response<RouteResponse> response) {
                    mRouteResponse = response.body();
                    if(mRouteResponse != null) {
                        mDistanceInKm = mRouteResponse.getTravelDistance();
                        if(mDistanceInKm != null) {
                            mDistanceInM = mRouteResponse.getTravelDistanceInMetres();
                            mKilometresTextView.setText(getString(R.string.distance_km, mDistanceInKm));
                            mMetresPointTextView.setText(getString(R.string.distance_m, mDistanceInM));
                        }
                        else {
                            mKilometresTextView.setVisibility(View.GONE);
                            mMetresPointTextView.setVisibility(View.GONE);
                        }

                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<RouteResponse> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    mKilometresTextView.setVisibility(View.GONE);
                    mMetresPointTextView.setVisibility(View.GONE);
                    mTopDescTextView.setText(R.string.error_downloading_distance_between_two_points);
                    mTopDescTextView.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorRed));
                }
            });
        }

        drawLineOnMap(firstGeoposition, secondGeoposition);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

        outState.putDouble("distanceInKm", mDistanceInKm);
        outState.putDouble("distanceInM", mDistanceInM);
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void drawLineOnMap(Geoposition firstGeoposition, Geoposition secondGeoposition) {
        List<Geoposition> geopoints = new ArrayList<>();
        geopoints.add(firstGeoposition);
        geopoints.add(secondGeoposition);

        MapPolyline mapPolyline = new MapPolyline();
        mapPolyline.setPath(new Geopath(geopoints));
        mapPolyline.setStrokeColor(Color.BLACK);
        mapPolyline.setStrokeWidth(3);
        mapPolyline.setStrokeDashed(true);

        MapElementLayer linesLayer = new MapElementLayer();
        linesLayer.setZIndex(1.0f);
        linesLayer.getElements().add(mapPolyline);
        mMapView.getLayers().add(linesLayer);


        MapElementLayer pinLayer = new MapElementLayer();
        mMapView.getLayers().add(pinLayer);

        Geopoint firstGeopoint = new Geopoint(firstGeoposition);
        Geopoint secondGeopoint = new Geopoint(secondGeoposition);

        MapIcon firstPushpin = new MapIcon();
        firstPushpin.setLocation(firstGeopoint);
        firstPushpin.setTitle(getString(R.string.first_point));
        pinLayer.getElements().add(firstPushpin);

        MapIcon secondPushpin = new MapIcon();
        secondPushpin.setLocation(secondGeopoint);
        secondPushpin.setTitle(getString(R.string.second_point));
        pinLayer.getElements().add(secondPushpin);

        mMapView.setScene(MapScene.createFromLocationsAndMargin(Arrays.asList(firstGeopoint, secondGeopoint), 50),
                MapAnimationKind.NONE);
    }

    private String geoPositionToString(Geoposition geoposition) {
        return geoposition.getLatitude() + "," + geoposition.getLongitude();
    }
}
