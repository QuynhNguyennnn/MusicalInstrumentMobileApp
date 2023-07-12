package com.example.highmusicapp.ActivityController;

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

        MenuItem menuItem = menu.findItem(R.id.menuCart);
        View actionView = menuItem.getActionView();

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
    }
}