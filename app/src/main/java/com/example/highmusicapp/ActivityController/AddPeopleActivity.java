package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.R;

public class AddPeopleActivity extends AppCompatActivity {
    EditText fullName, phoneNumber, address;
    String txt_fullName,txt_phone, txt_address;
    Button btnNext;
    private TextView already_txt_1;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        fullName = findViewById(R.id.fullNameInput);
        phoneNumber = findViewById(R.id.phoneInput);
        address = findViewById(R.id.addressInput);
        already_txt_1 = findViewById(R.id.already_txt_1);


        btnNext = findViewById(R.id.next_btn);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_fullName = fullName.getText().toString().trim();
                txt_phone = phoneNumber.getText().toString().trim();
                txt_address = address.getText().toString().trim();
                if (txt_fullName.isEmpty() || txt_phone.isEmpty() || txt_address.isEmpty()){
                    Toast.makeText(AddPeopleActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else if (txt_phone.length() != 10){
                    Toast.makeText(AddPeopleActivity.this, "Phone Number wrong format!", Toast.LENGTH_SHORT).show();
                } else {
                    People people = new People(txt_fullName, txt_phone, txt_address, true);
                    Intent intent = new Intent(AddPeopleActivity.this, RegisterActivity.class);
                    intent.putExtra("peopleModel", people);
                    startActivity(intent);
                }
            }
        });

        already_txt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPeopleActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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
            Intent intent = new Intent(AddPeopleActivity.this, ViewProductActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.location_nav) {
            Intent intent = new Intent(AddPeopleActivity.this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.bill_nav) {
            Intent intent = new Intent(AddPeopleActivity.this, BillActivity.class);
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
                    Intent intent = new Intent(AddPeopleActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            });
            return true;
        } else if (item.getItemId() == R.id.logout_nav) {
            preferences = getSharedPreferences("MIA", MODE_PRIVATE);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(AddPeopleActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_nav) {
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}