package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class MapFragment extends Fragment
{

    private int requestCode;
    private SupportMapFragment supportMapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Initialize map fragment
        supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap)
            {
                // check map permissions and ask for them if not given
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                   ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    googleMap.setMyLocationEnabled(true);
                }
                else
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
                }
                //When map is loaded
                googleMap.setMyLocationEnabled(true);

                placeFavourites(googleMap);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.userLocation, 15), 2000, null);

                // on click on map set proximitystations from stationAPI as marker
                googleMap.setOnMapClickListener(latLng ->{
                    googleMap.clear();
                    MainActivity.userLocation = new LatLng(latLng.latitude, latLng.longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    Marker userLocation = googleMap.addMarker(markerOptions);

                    ArrayList<Ladestation> filtered = StationAPI.getProximityStations(GlobalStorage.getAllStations() ,markerOptions.getPosition().latitude, markerOptions.getPosition().longitude, 10);

                    filtered.forEach(ladestation -> {
                        LatLng pos = new LatLng(ladestation.getLat(),ladestation.getLon());
                        MarkerOptions _markerOptions = new MarkerOptions();
                        _markerOptions.position(pos);
                        if(GlobalStorage.getDefStations().contains(ladestation.id)){
                            _markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            if(!GlobalStorage.getFavStations().contains(ladestation.id)){
                                _markerOptions.title(getResources().getString(R.string.broken) + "! " + ladestation.getLocation());
                            }
                            else{
                                _markerOptions.title(getResources().getString(R.string.favouriteDefect) + " - " + ladestation.getLocation());
                            }
                        }
                        else{
                            _markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            _markerOptions.title(getResources().getString(R.string.station) + " - " + ladestation.getLocation());
                        }

                        String snippetString =ladestation.getStreet() + " " + ladestation.getNumber();
                        if (ladestation.getAdditional() != null)
                            snippetString = snippetString + ladestation.getAdditional();
                        snippetString = snippetString + " - " + ladestation.getArea();
                        _markerOptions.snippet(snippetString);
                        googleMap.addMarker(_markerOptions);
                    });
                    placeFavourites(googleMap);
                    userLocation.remove();

                });
            }
        });
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentOrientation = getResources().getConfiguration().orientation;
        if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }

    private void placeFavourites(GoogleMap googleMap){
        for(Ladestation ladestation : GlobalStorage.getAllStations()) {
            if(GlobalStorage.getFavStations().contains(ladestation.id)) {
                MarkerOptions markerOptionsFav = new MarkerOptions();
                markerOptionsFav.position(new LatLng(ladestation.getLat(), ladestation.getLon()));
                if(GlobalStorage.getDefStations().contains(ladestation.id)){
                    markerOptionsFav.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    markerOptionsFav.title(getResources().getString(R.string.favouriteDefect) + " - " + ladestation.getLocation());
                }
                else{
                    markerOptionsFav.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    markerOptionsFav.title(getResources().getString(R.string.favourite) + " - " + ladestation.getLocation());
                }


                String snippetString =ladestation.getStreet() + " " + ladestation.getNumber();
                if (ladestation.getAdditional() != null)
                    snippetString = snippetString + ladestation.getAdditional();
                snippetString = snippetString + " - " + ladestation.getArea();
                markerOptionsFav.snippet(snippetString);
                googleMap.addMarker(markerOptionsFav);
            }
        }
    }
}
