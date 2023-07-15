package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.highmusicapp.ActivityFragments.BillFragment;
import com.example.highmusicapp.ActivityFragments.ChatFragment;
import com.example.highmusicapp.ActivityFragments.HomeFragment;
import com.example.highmusicapp.ActivityFragments.LocationFragment;
import com.example.highmusicapp.ActivityFragments.LoginFragment;
import com.example.highmusicapp.ActivityFragments.LogoutFragment;
import com.example.highmusicapp.AdapterController.BillDetailAdapter;
import com.example.highmusicapp.Dao.Bill_ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill_Product;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ViewBillDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private HighMusicDatabase highMusicDatabase;
    private Bill_ProductDAO billProductDAO;
    private BillDetailAdapter billDetailAdapter;
    private SharedPreferences preferences;
    int billID;
    private Context context = this;
    RecyclerView billDetailRecyclerView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill_detail);
        billDetailRecyclerView = (RecyclerView) findViewById(R.id.billDetailRecycler);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);

        billDetailAdapter = new BillDetailAdapter(this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        billProductDAO = highMusicDatabase.getBillProductDAO();

         billID = (int) getIntent().getSerializableExtra("billID");

        billDetailRecyclerView.setAdapter(billDetailAdapter);
        billDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarBD);
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

    @Override
    protected void onResume(){
        super.onResume();
        fetchBillDetailData();
    }

    private void fetchBillDetailData() {
        billDetailAdapter.clearBillDetail();
        List<Bill_Product> list = billProductDAO.getAllProductFromBill(billID);

        for (int i = 0; i < list.size(); i++) {
            Bill_Product billProduct = list.get(i);
            billDetailAdapter.addBillDetail(billProduct);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menuCart);
        View actionView = menuItem.getActionView();

        TextView txtQuantityCart = actionView.findViewById(R.id.txtQuantityCart);

        txtQuantityCart.setText(preferences.getString("cartQuantity", "-1"));
        if(Integer.parseInt(preferences.getString("cartQuantity", "-1")) == 0)
        {
            txtQuantityCart.setVisibility(View.GONE);
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }
}