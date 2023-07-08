package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

public class CreateProductActivity extends AppCompatActivity implements ProductListener{

    EditText productID,
            productName,
            productCategoryID,
            productPrice,
            productMaterial,
            productQuantity,
            productDescription,
            productYearOfManufacture,
            productImage,
            productStatus;

    Button insertProductBtn;

    private HighMusicDatabase highMusicDatabase;
    private ProductDAO productDAO;

    private ProductAdapter productAdapter;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        productName = (EditText) findViewById(R.id.productName);
        productCategoryID = (EditText) findViewById(R.id.productCategoryID);
        productPrice = (EditText) findViewById(R.id.productPrice);
        productMaterial = (EditText) findViewById(R.id.productMaterial);
        productQuantity = (EditText) findViewById(R.id.productQuantity);
        productDescription = (EditText) findViewById(R.id.productDescription);
        productYearOfManufacture = (EditText) findViewById(R.id.productYearOfManufacture);
        productImage = (EditText) findViewById(R.id.productImage);
        productStatus = (EditText) findViewById(R.id.productStatus);
        insertProductBtn = (Button) findViewById(R.id.insertProductBtn);

        productAdapter = new ProductAdapter(this, (ProductListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        productDAO = highMusicDatabase.getProductDAO();

        insertProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getDataProductName = productName.getText().toString();
                String getDataProductCategoryID = productCategoryID.getText().toString();
                String getDataProductPrice = productPrice.getText().toString();
                String getDataProductMaterial = productMaterial.getText().toString();
                String getDataProductQuantity = productQuantity.getText().toString();
                String getDataProductDescription = productDescription.getText().toString();
                String getDataProductYearOfManufacture = productYearOfManufacture.getText().toString();
                String getDataProductImage = productImage.getText().toString();
                String getDataProductStatus = productStatus.getText().toString();

                try {
                    // Validate input data
                    if (
                            getDataProductName.isEmpty() ||
                            getDataProductCategoryID.isEmpty() ||
                            getDataProductPrice.isEmpty() ||
                            getDataProductMaterial.isEmpty() ||
                            getDataProductQuantity.isEmpty() ||
                            getDataProductDescription.isEmpty() ||
                            getDataProductYearOfManufacture.isEmpty() ||
                            getDataProductImage.isEmpty() ||
                            getDataProductStatus.isEmpty()) {
                        Toast.makeText(CreateProductActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        onResume();
                    }

                    // Create Data object and add to adapter and database
                    Product product = new Product(
                            Double.parseDouble(getDataProductPrice),
                            Integer.parseInt(getDataProductCategoryID),
                            getDataProductName,
                            getDataProductMaterial,
                            Integer.parseInt(getDataProductQuantity),
                            Integer.parseInt(getDataProductYearOfManufacture),
                            getDataProductDescription,
                            getDataProductImage,
                            Boolean.parseBoolean(getDataProductStatus)
                    );

                    Log.d("Products: ", product.toString());

                    productAdapter.addProduct(product);
                    productDAO.insertProduct(product);
                    Toast.makeText(CreateProductActivity.this, "Insert successfully!", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    productName.setText("");
                    productCategoryID.setText("");
                    productPrice.setText("");
                    productMaterial.setText("");
                    productQuantity.setText("");
                    productDescription.setText("");
                    productYearOfManufacture.setText("");
                    productImage.setText("");
                    productStatus.setText("");
                } catch (Exception ex) {
                    Toast.makeText(CreateProductActivity.this, "Inser fail, please check your data!", Toast.LENGTH_SHORT).show();
                    Log.d("Create Product Err: ", ex.getMessage());
                    onResume();
                }
            }
        });

    }

    @Override
    public void onUpdateProduct(Product product) {

    }

    @Override
    public void onDeleteProduct(int id, int pos) {

    }
}