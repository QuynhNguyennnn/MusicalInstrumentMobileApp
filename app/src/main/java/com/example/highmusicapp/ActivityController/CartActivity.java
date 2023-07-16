package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.highmusicapp.AdapterController.CartAdapter;
import com.example.highmusicapp.AdapterController.CartListener;
import com.example.highmusicapp.Dao.BillDAO;
import com.example.highmusicapp.Dao.Bill_ProductDAO;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.Dao.Cart_ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.MainActivity;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Bill_Product;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener{
    private HighMusicDatabase highMusicDatabase;
    private CartDAO cartDAO;
    private Cart_ProductDAO cart_productDAO;
    private float total = 0;
    private CartAdapter cartAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private BillDAO billDAO;
    private Bill_ProductDAO billProductDAO;
    private Context context  = this;
    TextView txtTotal;
    Button btnPay;
    Intent intent;
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
        billDAO = highMusicDatabase.getBillDAO();
        billProductDAO = highMusicDatabase.getBillProductDAO();
        cart_productDAO = highMusicDatabase.getCart_ProductDAO();
        cartRecyclerView = (RecyclerView) findViewById(R.id.cartRecycler);
        cartAdapter = new CartAdapter(this,(int) cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)),(CartListener) this);


        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1))) != 0) {
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = date.format(formatter);

                    Bill bill = new Bill(preferences.getInt("id", 1), formattedDate, total,true);
                    long billID = billDAO.createBill(bill);
                    List<Product> productList = cartDAO.getProductsFromCart(preferences.getInt("id", 1));
                    for(Product product: productList){
                        Bill_Product billProduct = new Bill_Product((int)billID,product.getProductID(),product.getQuantity(),true);
                        billProductDAO.insertBill(billProduct);
                        cart_productDAO.pay((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id",1)), product.getProductID());
                    }
                    Toast.makeText(context, "Pay successful", Toast.LENGTH_SHORT).show();
                    intent = new Intent(context, ViewProductActivity.class);
                    editor.putString("cartQuantity", "0");
                    editor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Cart is empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        editor.putString("cartQuantity",String.valueOf(cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)))));
        editor.commit();
        invalidateOptionsMenu();
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
            editor.putString("cartQuantity",String.valueOf(cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(preferences.getInt("id", 1)))));
            editor.commit();
            invalidateOptionsMenu();
        }
        fetchCartData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem cart = menu.findItem(R.id.menuCart);
        View actionView = cart.getActionView();

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
            Intent intent = new Intent(CartActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.chat_nav) {
            Intent intent = new Intent(CartActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(CartActivity.this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.bill_nav) {
            Intent intent = new Intent(CartActivity.this, BillActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.logout_nav) {
            preferences = getSharedPreferences("MIA", MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}