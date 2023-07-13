package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.Dao.Cart_ProductDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.R;

import jp.wasabeef.blurry.Blurry;

public class LoginActivity extends AppCompatActivity {

    private EditText username_login;
    private EditText password_login;
    private Button login_btn;
    private TextView signup_txt;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private HighMusicDatabase highMusicDatabase;
    private AccountDAO accountDAO;
    private Cart_ProductDAO cart_productDAO;
    private CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();

        username_login = findViewById(R.id.username_login);
        password_login = findViewById(R.id.password_login);
        login_btn = findViewById(R.id.login_btn);
        signup_txt = findViewById(R.id.signup_txt);

        if (preferences.contains("username") && preferences.contains("role")) {
            Intent intent = new Intent(LoginActivity.this, ViewProductActivity.class);
            startActivity(intent);
        } else {
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = username_login.getText().toString().trim();
                    String password = password_login.getText().toString().trim();
                    highMusicDatabase = HighMusicDatabase.getInstance(LoginActivity.this);
                    accountDAO = highMusicDatabase.getAccountDAO();
                    cart_productDAO = highMusicDatabase.getCart_ProductDAO();
                    cartDAO = highMusicDatabase.getCartDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Account account = accountDAO.checkLogin(username, password);
                            if (username.isEmpty() || password.isEmpty()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (account == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                editor.putInt("id", account.getPeopleID());
                                editor.putString("username", account.getUsername());
                                editor.putString("role", String.valueOf(account.getRole()));
                                editor.putString("cartQuantity",String.valueOf(cart_productDAO.countProductInCart((int)cartDAO.getCartIDByCustomerID(account.getPeopleID()))));
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, ViewProductActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).start();
                }
            });

            signup_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, AddPeopleActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}