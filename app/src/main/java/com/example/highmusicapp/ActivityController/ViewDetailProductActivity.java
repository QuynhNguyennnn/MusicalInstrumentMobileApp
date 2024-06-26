package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewDetailProductActivity extends AppCompatActivity {
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

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context = this;

    public ViewDetailProductActivity() {

    }

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
                if (!preferences.contains("username")) {
                    Toast.makeText(context, "You must login first!", Toast.LENGTH_SHORT).show();
                } else {
                    if (cartDAO.findCartByCustomerID(preferences.getInt("id", 1))) {
                        if (cart_productDAO.findProductInCart((int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID())) {
                            cart_productDAO.increaseQuantity((int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID());
                        } else {
                            Cart_Product cart_product = new Cart_Product((int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)), product.getProductID(), 1, true);
                            cart_productDAO.addProductToCart(cart_product);
                        }
                    } else {
                        Cart cart = new Cart(preferences.getInt("id", 1), true);
                        int cartID = (int) cartDAO.createCart(cart);
                        Cart_Product cart_product = new Cart_Product(cartID, product.getProductID(), 1, true);
                        cart_productDAO.addProductToCart(cart_product);
                    }
                    Toast.makeText(context, "Add to cart successful", Toast.LENGTH_SHORT).show();
                    intent = new Intent(context, ViewProductActivity.class);
                    editor.putString("cartQuantity", String.valueOf(cart_productDAO.countProductInCart((int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)))));
                    editor.commit();
                    invalidateOptionsMenu();
                    startActivity(intent);
                }
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
        MenuItem cart = menu.findItem(R.id.menuCart);
        View actionView = cart.getActionView();

        TextView txtQuantityCart = actionView.findViewById(R.id.txtQuantityCart);

        txtQuantityCart.setText(preferences.getString("cartQuantity", "0"));
        if(Integer.parseInt(preferences.getString("cartQuantity", "0")) == 0)
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

        if (preferences.contains("username")) {
            MenuItem menuItem = menu.findItem(R.id.login_nav);
            menuItem.setVisible(false);
        } else {
            MenuItem menuItem = menu.findItem(R.id.logout_nav);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_nav) {
            Intent intent = new Intent(ViewDetailProductActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.chat_nav) {
            Intent intent = new Intent(ViewDetailProductActivity.this, ChatActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(ViewDetailProductActivity.this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.bill_nav) {
            Intent intent = new Intent(ViewDetailProductActivity.this, BillActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.logout_nav) {
            preferences = getSharedPreferences("MIA", MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(ViewDetailProductActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            Intent intent = new Intent(ViewDetailProductActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}