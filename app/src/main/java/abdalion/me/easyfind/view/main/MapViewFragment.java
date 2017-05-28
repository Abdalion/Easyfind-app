package abdalion.me.easyfind.view.main;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import abdalion.me.easyfind.Listener;
import abdalion.me.easyfind.R;
import abdalion.me.easyfind.model.User;

import static abdalion.me.easyfind.utils.Utils.isNull;

/**
 * Created by Egon on 27/4/2017.
 */

public class MapViewFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap ;
    private Marker mUserMarker ;
    private Listener<Boolean> mapFinishedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.maps_mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMyLocationEnabled(true);
                mapFinishedListener.update(true);

            }
        });

        return rootView;
    }

    public void setMapFinishedListener(Listener<Boolean> mapListener) {
        mapFinishedListener = mapListener;
    }

    public void setUserMarker(User user) {
        if(isNull(mUserMarker)) {
            createMarker(user);
        }
        else {
            if (mUserMarker.getTitle().equals(user.getMail())) {
                relocateMarker(user, false);
            }
            else {
                googleMap.clear();
                createMarker(user);
            }
        }
    }

    private void createMarker(User user) {
        String userLocation = user.getLocation();
        String[] latlng = userLocation.split(", ");
        LatLng userLatLng = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
        mUserMarker = googleMap.addMarker(new MarkerOptions().position(userLatLng).title(user.getMail()));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(userLatLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void relocateMarker(User user, boolean autoZoom) {
        if(user != null) {
            String userLocation = user.getLocation();
            String[] latlng = userLocation.split(", ");
            LatLng userLatLng = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));

            animateMarker(mUserMarker, userLatLng, false);

            if(autoZoom) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(userLatLng).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        }
    }

    private void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}