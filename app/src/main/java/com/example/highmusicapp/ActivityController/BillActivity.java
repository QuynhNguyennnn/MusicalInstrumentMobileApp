package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.Toast;

import com.example.highmusicapp.AdapterController.BillAdapter;
import com.example.highmusicapp.AdapterController.BillListener;
import com.example.highmusicapp.Dao.BillDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.MainActivity;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.R;

import java.util.List;

public class BillActivity extends AppCompatActivity implements BillListener {

    private HighMusicDatabase highMusicDatabase;
    private BillDAO billDAO;
    private BillAdapter billAdapter;
    private SharedPreferences preferences;
    private Context context = this;

    SharedPreferences.Editor editor;

    RecyclerView billRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        billRecyclerView = (RecyclerView) findViewById(R.id.billRecyclerView);
        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        billAdapter = new BillAdapter(this, (BillListener) this);
        highMusicDatabase = HighMusicDatabase.getInstance(this);
        billDAO = highMusicDatabase.getBillDAO();

        if (!preferences.contains("username")) {
            Toast.makeText(context, "You must logi first!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BillActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        billRecyclerView.setAdapter(billAdapter);
        billRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        billRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProductData();
    }

    private void fetchProductData() {
        billAdapter.clearBill();
        List<Bill> billList = billDAO.getAllBillByCustomerID(preferences.getInt("id",1));

        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            billAdapter.addBill(bill);
        }
    }

    @Override
    public void onViewBillDetail(int billID){
        Intent intent = new Intent(this, ViewBillDetailActivity.class);
        intent.putExtra("billID", billID);
        startActivity(intent);
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
            Intent intent = new Intent(BillActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.chat_nav) {
            Intent intent = new Intent(BillActivity.this, ChatActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(BillActivity.this, MapsActivity.class);
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
            Intent intent = new Intent(BillActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            Intent intent = new Intent(BillActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}