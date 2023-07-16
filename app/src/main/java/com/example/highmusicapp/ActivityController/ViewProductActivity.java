package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity implements ProductListener {
    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;
    private ProductAdapter productAdapter;

    private SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private Context context = this;

    RecyclerView productRecyclerView;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initUI();
        if (preferences.contains("username")) {
            Toast.makeText(context, "Welcome " + preferences.getString("username", ""), Toast.LENGTH_SHORT).show();
        }

        productRecyclerView = (RecyclerView) findViewById(R.id.productRecycler);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);

        productAdapter = new ProductAdapter(this, (ProductListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();

        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEvent();
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
//        MenuItem item = findViewById(R.id.menuCart);

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
            recreate();
            return true;
        } else if (item.getItemId() == R.id.chat_nav) {
            Intent intent = new Intent(ViewProductActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(ViewProductActivity.this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.bill_nav) {
            Intent intent = new Intent(ViewProductActivity.this, BillActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menuCart) {
            View actionView = item.getActionView();

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
        } else if (item.getItemId() == R.id.logout_nav) {
            preferences = getSharedPreferences("MIA", MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(ViewProductActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            Intent intent = new Intent(ViewProductActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}