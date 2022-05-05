package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    private static NavigationBarView bottomNav;
    private int requestCode;

    public static NavigationBarView getBottomNav() {
        return bottomNav;
    }
    public static boolean onServiceFragment = false;
    public static LatLng userLocation = new LatLng(49.872768, 8.651180);


    /**
     * Function triggers on create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this , this.getResources().getString(R.string.errLocation), Toast.LENGTH_LONG).show();
            }
        }

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        StationAPI.initialize(getApplicationContext());
        Toast.makeText(this, Integer.toString(GlobalStorage.getAllStations().size()), Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginFragment()).commit();

        bottomNav.setVisibility(View.INVISIBLE);

    }

    private NavigationBarView.OnItemSelectedListener navListener =
    item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                onServiceFragment = false;
                break;
            case R.id.nav_map:
                selectedFragment = new MapFragment();
                onServiceFragment = false;
                break;
            case R.id.nav_service:
                selectedFragment = new ServiceFragment();
                onServiceFragment = true;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).addToBackStack(null).commit();

        return true;
    };
}