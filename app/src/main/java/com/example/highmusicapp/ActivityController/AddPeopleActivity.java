package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.R;

public class AddPeopleActivity extends AppCompatActivity {
    EditText fullName, phoneNumber, address;
    String txt_fullName,txt_phone, txt_address;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        fullName = findViewById(R.id.fullNameInput);
        phoneNumber = findViewById(R.id.phoneInput);
        address = findViewById(R.id.addressInput);


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
    }
}