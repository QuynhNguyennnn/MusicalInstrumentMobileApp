package com.example.highmusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.highmusicapp.ActivityController.AddPeopleActivity;
import com.example.highmusicapp.ActivityController.BillActivity;
import com.example.highmusicapp.ActivityController.CartActivity;
import com.example.highmusicapp.ActivityController.LoginActivity;
import com.example.highmusicapp.ActivityController.LogoutActivity;
import com.example.highmusicapp.ActivityController.RegisterActivity;
import com.example.highmusicapp.ActivityController.ViewProductActivity;
import com.example.highmusicapp.ActivityFragments.BillFragment;
import com.example.highmusicapp.ActivityFragments.ChatFragment;
import com.example.highmusicapp.ActivityFragments.HomeFragment;
import com.example.highmusicapp.ActivityFragments.LocationFragment;
import com.example.highmusicapp.ActivityFragments.LoginFragment;
import com.example.highmusicapp.ActivityFragments.LogoutFragment;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.CustomerDAO;
import com.example.highmusicapp.Dao.PeopleDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.Models.Category;
import com.example.highmusicapp.Models.Customer;
import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.Models.Role;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Intent intent;
    Button button;
    HighMusicDatabase highMusicDatabase;
    CategoryDAO categoryDAO;
    ProductDAO productDAO;
    AccountDAO accountDAO;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PeopleDAO peopleDAO;
    CustomerDAO customerDAO;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.commit();
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home_nav);
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menuCart);
        View actionView = menuItem.getActionView();

        TextView txtQuantityCart = actionView.findViewById(R.id.txtQuantityCart);
        txtQuantityCart.setVisibility(View.GONE);
        return true;
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}