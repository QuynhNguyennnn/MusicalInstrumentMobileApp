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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.highmusicapp.ActivityFragments.BillFragment;
import com.example.highmusicapp.ActivityFragments.ChatFragment;
import com.example.highmusicapp.ActivityFragments.HomeFragment;
import com.example.highmusicapp.ActivityFragments.LocationFragment;
import com.example.highmusicapp.ActivityFragments.LoginFragment;
import com.example.highmusicapp.ActivityFragments.LogoutFragment;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity implements ProductListener, NavigationView.OnNavigationItemSelectedListener{
    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;

    private SharedPreferences preferences;
    private Context context = this;

    RecyclerView productRecyclerView;
    private EditText edtSearch;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initUI();

        productRecyclerView = (RecyclerView) findViewById(R.id.productRecycler);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);

        productAdapter = new ProductAdapter(this, (ProductListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();

        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEvent();

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(ViewProductActivity.this);
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

    private void initUI() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
    }

    private void clearInput() {
        edtSearch.setText("");
    }

    private void handleSearchData() {
        String keyword = edtSearch.getText().toString().trim();

        productAdapter.clearProduct();
        List<Product> productListSearch = productDAO.searchProducts(keyword);

        for (int i = 0; i < productListSearch.size(); i++) {
            Product product = productListSearch.get(i);
            productAdapter.addProduct(product);
        }
        clearInput();
        hideSoftKeyBoard();
    }

    private void searchEvent(){
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //logic search
                    handleSearchData();
                }
                return false;
            }
        });
    }

    // For refresh after execute action
    @Override
    protected void onResume() {
        super.onResume();
        fetchProductData();
    }

    // For Working with Database
    private void fetchProductData() {
        productAdapter.clearProduct();
        List<Product> productList = productDAO.getAllProduct();

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            productAdapter.addProduct(product);
        }
    }

    @Override
    public void onUpdateProduct(Product product) {

    }

    @Override
    public void onDeleteProduct(int id, int pos) {

    }

    @Override
    public void onViewDetailProduct(Product product) {
        Intent intent = new Intent(this, ViewDetailProductActivity.class);
        intent.putExtra("productModel", product);
        startActivity(intent);
    }

    public void hideSoftKeyBoard() { //an ban phim
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (NullPointerException ex)  {
            ex.printStackTrace();
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