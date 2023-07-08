package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewProductActivity extends AppCompatActivity implements ProductListener {
    FloatingActionButton addProductFloatingActionButton;

    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;

    private ProductAdapter productAdapter;

    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        addProductFloatingActionButton = (FloatingActionButton) findViewById(R.id.AddProductFloatingActionButton);
        productRecyclerView = (RecyclerView) findViewById(R.id.productRecycler);

        productAdapter = new ProductAdapter(this, (ProductListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();

        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // For floating action button to switch to CreateProductActivity
        addProductFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProductActivity.this, CreateProductActivity.class);
                startActivity(intent);
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
}