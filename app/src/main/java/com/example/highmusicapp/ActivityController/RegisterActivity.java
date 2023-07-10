package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class RegisterActivity extends AppCompatActivity {

    private EditText email_input;
    private EditText username_input;
    private EditText password_input;
    private EditText confirm_input;
    private Button register_btn;
    private TextView already_txt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private AccountDAO accountDAO;
    private HighMusicDatabase highMusicDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();

        email_input = findViewById(R.id.email_input);
        username_input = findViewById(R.id.username_input);
        password_input = findViewById(R.id.password_input);
        confirm_input = findViewById(R.id.confirm_input);
        register_btn = findViewById(R.id.register_btn);
        already_txt = findViewById(R.id.already_txt);

        if (preferences.contains("username") && preferences.contains("role")) {
            Intent intent = new Intent(RegisterActivity.this, ViewProductActivity.class);
            startActivity(intent);
        } else {
            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = email_input.getText().toString().trim();
                    String username = username_input.getText().toString().trim();
                    String password = password_input.getText().toString().trim();
                    String confirm = confirm_input.getText().toString().trim();

                    if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirm)) {
                        Toast.makeText(RegisterActivity.this, "Confirm password is not matched!", Toast.LENGTH_SHORT).show();
                    } else {
                        highMusicDatabase = HighMusicDatabase.getInstance(RegisterActivity.this);
                        accountDAO = highMusicDatabase.getAccountDAO();
                        Account accountCheck = accountDAO.checkUsername(username);
                        if (accountCheck != null) {
                            Toast.makeText(RegisterActivity.this, "Username is already existed.", Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Account account = new Account();
                                    account.setEmail(email);
                                    account.setUsername(username);
                                    account.setPassword(password);
                                    account.setRole(2);
                                    account.setStatus(true);
                                    accountDAO.register(account);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegisterActivity.this, "Register suceessful!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    editor.putString("username", account.getUsername());
                                    editor.putString("role", String.valueOf(account.getRole()));
                                    editor.commit();
                                    Intent intent = new Intent(RegisterActivity.this, ViewProductActivity.class);
                                    startActivity(intent);
                                }
                            }).start();
                        }
                    }
                }
            });

            already_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}