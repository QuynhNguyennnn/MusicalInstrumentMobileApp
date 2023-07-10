package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.highmusicapp.AdapterController.CartAdapter;
import com.example.highmusicapp.AdapterController.CartListener;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.Dao.Cart_ProductDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener{
    private HighMusicDatabase highMusicDatabase;
    private CartDAO cartDAO;
    private Cart_ProductDAO cart_productDAO;
    private float total = 0;
    private CartAdapter cartAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    TextView txtTotal;
    Button btnPay;
    RecyclerView cartRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        txtTotal = findViewById(R.id.txtTotal);
        btnPay = findViewById(R.id.btnPay);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        cartDAO = highMusicDatabase.getCartDAO();
        cart_productDAO = highMusicDatabase.getCart_ProductDAO();
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecycler);
        cartAdapter = new CartAdapter(this,(int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)),(CartListener) this);


        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchCartData();
    }

    private void fetchCartData() {
        cartAdapter.clearProduct();
        List<Product> productList = cartDAO.getProductsFromCart(preferences.getInt("id", 1));

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            total += product.getQuantity() * product.getPrice();
            cartAdapter.addProduct(product);
        }
        txtTotal.setText("Total :"+String.valueOf(total)+"$");
    }

    @Override
    public void onRemoveProduct(int cartID, int productID) {
        cart_productDAO.deleteProductToCart(cartID, productID);
        fetchCartData();
    }

    @Override
    public void onIncrease(int cartID, int productID) {
        cart_productDAO.increaseQuantity(cartID, productID);
        System.out.println(cart_productDAO.findQuantityProductInCart(cartID, productID));
        fetchCartData();
    }

    @Override
    public void onDecrease(int cartID, int productID) {
        int quantity = cart_productDAO.findQuantityProductInCart(cartID, productID);
        System.out.println(cart_productDAO.findQuantityProductInCart(cartID, productID));
        if (quantity > 1){
            cart_productDAO.decreaseQuantity(cartID, productID);
        } else {
            cart_productDAO.deleteProductToCart(cartID, productID);
        }
        fetchCartData();
    }
}