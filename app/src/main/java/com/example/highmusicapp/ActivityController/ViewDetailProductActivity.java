package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
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
    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    private ProductAdapter productAdapter;

    private Product product;

    public ViewDetailProductActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_product);

        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();

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

        // Loading image view with gilde
        String imageUrl = product.getImage();
        Glide
                .with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholderimage)
                .into(productImage);
    }
}