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
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.highmusicapp.AdapterController.BillAdapter;
import com.example.highmusicapp.AdapterController.BillListener;
import com.example.highmusicapp.Dao.BillDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class BillActivity extends AppCompatActivity implements BillListener, NavigationView.OnNavigationItemSelectedListener {

    private HighMusicDatabase highMusicDatabase;
    private BillDAO billDAO;
    private BillAdapter billAdapter;
    private SharedPreferences preferences;
    private Context context = this;
    RecyclerView billRecyclerView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        billRecyclerView = (RecyclerView) findViewById(R.id.billRecyclerView);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        billAdapter = new BillAdapter(this, (BillListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        billDAO = highMusicDatabase.getBillDAO();

        billRecyclerView.setAdapter(billAdapter);
        billRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        billRecyclerView.addItemDecoration(itemDecoration);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarBill);
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
    protected void onResume() {
        super.onResume();
        fetchProductData();
    }

    private void fetchProductData() {
        billAdapter.clearBill();
        List<Bill> billList = billDAO.getAllBillByCustomerID(preferences.getInt("id",1));

        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            billAdapter.addBill(bill);
        }
    }

    @Override
    public void onViewBillDetail(int billID){
        Intent intent = new Intent(this, ViewBillDetailActivity.class);
        intent.putExtra("billID", billID);
        startActivity(intent);
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