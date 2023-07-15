package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.ActivityFragments.BillFragment;
import com.example.highmusicapp.ActivityFragments.ChatFragment;
import com.example.highmusicapp.ActivityFragments.HomeFragment;
import com.example.highmusicapp.ActivityFragments.LocationFragment;
import com.example.highmusicapp.ActivityFragments.LoginFragment;
import com.example.highmusicapp.ActivityFragments.LogoutFragment;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.Dao.Cart_ProductDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Cart;
import com.example.highmusicapp.Models.Cart_Product;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.navigation.NavigationView;

public class ViewDetailProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView
            productName,
            productCategoryID,
            productPrice,
            productMaterial,
            productQuantity,
            productYearOfManufacture,
            productDescription,
            productStatus;
    ImageView productImage;
    Button addToCartBtn;
    Intent intent;
    private CartDAO cartDAO;
    private Cart_ProductDAO cart_productDAO;
    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductAdapter productAdapter;
    private Product product;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context = this;

    public ViewDetailProductActivity() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_product);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();

        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();
        cartDAO = highMusicDatabase.getCartDAO();
        cart_productDAO = highMusicDatabase.getCart_ProductDAO();

        //menubar
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Matching UI id
        productName = (TextView) findViewById(R.id.viewDetail_productName);
        productCategoryID = (TextView) findViewById(R.id.viewDetail_productCategoryID);
        productPrice = (TextView) findViewById(R.id.viewDetail_productPrice);
        productMaterial = (TextView) findViewById(R.id.viewDetail_productMaterial);
        productQuantity = (TextView) findViewById(R.id.viewDetail_productQuantity);
        productYearOfManufacture = (TextView) findViewById(R.id.viewDetail_yearOfManufacture);
        productDescription = (TextView) findViewById(R.id.viewDetail_productDescription);
        productStatus = (TextView) findViewById(R.id.viewDetail_productStatus);
        productImage = (ImageView) findViewById(R.id.viewDetail_productImage);

        // Load data of object to form
        product = (Product) getIntent().getSerializableExtra("productModel");
        // Set text base on product Model
        productName.setText(product.getProductName());
        productPrice.setText("Price: " + product.getPrice() + "$");
        productMaterial.setText(product.getMaterial() + "");
        productQuantity.setText("Quantity Available: " + product.getQuantity() + "");
        productYearOfManufacture.setText("Manufacture year: " + product.getYearOfManufacture() + "");
        productDescription.setText("Description: "+ product.getDescription());

        //Set Category Name by using category id of foreign key
        categoryDAO = highMusicDatabase.getCategoryDAO();
        String categoryNamebyId = categoryDAO.getCategoryNameById(product.getCategoryID());
        productCategoryID.setText(categoryNamebyId + "");

        // Set status product
        if(product.isStatus()) {
            productStatus.setText("Status: " + "Available");
        } else {
            productStatus.setText("Status: " + "Out of stock");
        }

        addToCartBtn = findViewById(R.id.viewDetail_addToCartBtn);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartDAO.findCartByCustomerID(preferences.getInt("id", 1))){
                    if (cart_productDAO.findProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID())){
                        cart_productDAO.increaseQuantity((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID());
                    } else {
                        Cart_Product cart_product = new Cart_Product((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID(), 1, true);
                        cart_productDAO.addProductToCart(cart_product);
                    }
                }else{
                    Cart cart = new Cart(preferences.getInt("id", 1), true);
                    cartDAO.createCart(cart);
                    Cart_Product cart_product = new Cart_Product((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID(), 1, true);
                    cart_productDAO.addProductToCart(cart_product);
                }
                Toast.makeText(context, "Add to cart successful", Toast.LENGTH_SHORT).show();
                intent = new Intent(context, ViewProductActivity.class);
                editor.putString("cartQuantity",String.valueOf(cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)))));
                editor.commit();
                invalidateOptionsMenu();
                startActivity(intent);
            }
        });

        // Loading image view with gilde
        String imageUrl = product.getImage();
        Glide
                .with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholderimage)
                .into(productImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menuCart);
        View actionView = menuItem.getActionView();

        TextView txtQuantityCart = actionView.findViewById(R.id.txtQuantityCart);

        txtQuantityCart.setText(String.valueOf(cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)))));
        if (cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1))) == 0){
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
}