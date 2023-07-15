package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.highmusicapp.ActivityFragments.BillFragment;
import com.example.highmusicapp.ActivityFragments.ChatFragment;
import com.example.highmusicapp.ActivityFragments.HomeFragment;
import com.example.highmusicapp.ActivityFragments.LocationFragment;
import com.example.highmusicapp.ActivityFragments.LoginFragment;
import com.example.highmusicapp.ActivityFragments.LogoutFragment;
import com.example.highmusicapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Spinner spinner;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frmMap);
        fusedLocationProviderClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);
        Dexter.withContext(getApplicationContext()).withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        addControls();

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarMap);
        setSupportActionBar(toolbar);

        /*drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);
        if ( id == R.id.home_nav) {
            replaceFragment(new HomeFragment());
        } else if (id == R.id.chat_nav) {
            replaceFragment(new ChatFragment());
        } else if (id == R.id.location_nav) {
            replaceFragment(new LocationFragment());
        } else if (id == R.id.bill_nav) {
            replaceFragment(new BillFragment());
        } else if (id == R.id.logout_nav) {
            replaceFragment(new LogoutFragment());
        } /*else if (id == R.id.login_nav) {
            replaceFragment(new LoginFragment());
        }*/
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(myLocation).title("My Location!");
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,30));

                            //tao vi tri
                            //21.065672470902758, 105.52512140954303 fpt hoa lac
                            LatLng fuHoaLac = new LatLng(21.065672470902758, 105.52512140954303);
                            //15.968718425972495, 108.26057825316745 fpt da nang
                            LatLng fuDaNang = new LatLng(15.968718425972495, 108.26057825316745);
                            //13.804124022705652, 109.21908102243833 fpt quy nhon
                            LatLng fuQuyNhon = new LatLng(13.804124022705652, 109.21908102243833);
                            //10.862165182269598, 106.81041577722421 fpt hcm
                            LatLng fuHCM = new LatLng(10.862165182269598, 106.81041577722421);
                            //10.01418807603898, 105.73179641350885 fpt can tho
                            LatLng fuCanTho = new LatLng(10.01418807603898, 105.73179641350885);


                            //ve hinh tron va to mau cho cac vi tri
                            Circle circle = googleMap.addCircle(new CircleOptions().center(fuHoaLac).radius(50).fillColor(Color.GREEN).strokeColor(Color.BLUE));
                            Circle circle1 = googleMap.addCircle(new CircleOptions().center(fuDaNang).radius(50).fillColor(Color.GREEN).strokeColor(Color.BLUE));
                            Circle circle2 = googleMap.addCircle(new CircleOptions().center(fuQuyNhon).radius(50).fillColor(Color.GREEN).strokeColor(Color.BLUE));
                            Circle circle3 = googleMap.addCircle(new CircleOptions().center(fuHCM).radius(50).fillColor(Color.GREEN).strokeColor(Color.BLUE));
                            Circle circle4 = googleMap.addCircle(new CircleOptions().center(fuCanTho).radius(50).fillColor(Color.GREEN).strokeColor(Color.BLUE));

                            //add Marker
                            googleMap.addMarker(new MarkerOptions().position(fuHoaLac).title("FU HOA LAC"));
                            googleMap.addMarker(new MarkerOptions().position(fuDaNang).title("FU DA NANG"));
                            googleMap.addMarker(new MarkerOptions().position(fuQuyNhon).title("FU QUY NHON"));
                            googleMap.addMarker(new MarkerOptions().position(fuHCM).title("FU HCM"));
                            googleMap.addMarker(new MarkerOptions().position(fuCanTho).title("FU CAN THO"));


                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                            break;
                                        case 1:
                                            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                            break;
                                        case 2:
                                            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                            break;
                                        case 3:
                                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                            break;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }
                        else {
                            Toast.makeText(MapsActivity.this, "Please on your Location App permissions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void addControls() {
        spinner = findViewById(R.id.spinner);

        ArrayList<String> list_StyleMap = new ArrayList<>();
        list_StyleMap.add("NORMAL Type");
        list_StyleMap.add("SATELLITE Type");
        list_StyleMap.add("TERRAIN Type");
        list_StyleMap.add("HYBRID Type");

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_StyleMap);
        spinner.setAdapter(arrayAdapter);
    }
}