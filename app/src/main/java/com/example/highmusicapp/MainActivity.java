package com.example.highmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.highmusicapp.ActivityController.AddPeopleActivity;
import com.example.highmusicapp.ActivityController.BillActivity;
import com.example.highmusicapp.ActivityController.CartActivity;
import com.example.highmusicapp.ActivityController.LoginActivity;
import com.example.highmusicapp.ActivityController.LogoutActivity;
import com.example.highmusicapp.ActivityController.RegisterActivity;
import com.example.highmusicapp.ActivityController.ViewProductActivity;
import com.example.highmusicapp.AdapterController.ProductAdapter;
import com.example.highmusicapp.AdapterController.ProductListener;
import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.CustomerDAO;
import com.example.highmusicapp.Dao.PeopleDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.Models.Category;
import com.example.highmusicapp.Models.Customer;
import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.Models.Role;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    Button button;
    HighMusicDatabase highMusicDatabase;
    CategoryDAO categoryDAO;
    ProductDAO productDAO;
    AccountDAO accountDAO;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PeopleDAO peopleDAO;
    CustomerDAO customerDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, AddPeopleActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menuCart);
        View actionView = menuItem.getActionView();

        TextView txtQuantityCart = actionView.findViewById(R.id.txtQuantityCart);
        txtQuantityCart.setVisibility(View.GONE);
        return true;
    }
}