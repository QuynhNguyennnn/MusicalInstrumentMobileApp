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
import android.widget.TextView;

import com.example.highmusicapp.AdapterController.BillDetailAdapter;
import com.example.highmusicapp.Dao.Bill_ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Bill_Product;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.List;

public class ViewBillDetailActivity extends AppCompatActivity {
    private HighMusicDatabase highMusicDatabase;
    private Bill_ProductDAO billProductDAO;
    private BillDetailAdapter billDetailAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    int billID;
    private Context context = this;
    RecyclerView billDetailRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill_detail);
        billDetailRecyclerView = (RecyclerView) findViewById(R.id.billDetailRecycler);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);

        billDetailAdapter = new BillDetailAdapter(this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        billProductDAO = highMusicDatabase.getBillProductDAO();

         billID = (int) getIntent().getSerializableExtra("billID");

        billDetailRecyclerView.setAdapter(billDetailAdapter);
        billDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchBillDetailData();
    }

    private void fetchBillDetailData() {
        billDetailAdapter.clearBillDetail();
        List<Bill_Product> list = billProductDAO.getAllProductFromBill(billID);

        for (int i = 0; i < list.size(); i++) {
            Bill_Product billProduct = list.get(i);
            billDetailAdapter.addBillDetail(billProduct);
        }
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
            Intent intent = new Intent(ViewBillDetailActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.chat_nav) {
            Intent intent = new Intent(ViewBillDetailActivity.this, ChatActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(ViewBillDetailActivity.this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.bill_nav) {
            recreate();
            return true;
        } else if (item.getItemId() == R.id.logout_nav) {
            preferences = getSharedPreferences("MIA", MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(ViewBillDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            Intent intent = new Intent(ViewBillDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}