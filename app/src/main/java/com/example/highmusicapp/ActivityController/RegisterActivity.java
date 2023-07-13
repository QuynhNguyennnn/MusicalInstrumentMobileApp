package com.example.highmusicapp.ActivityController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.Dao.PeopleDAO;
import com.example.highmusicapp.HighMusicDatabase;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.Models.Role;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private PeopleDAO peopleDAO;

    private HighMusicDatabase highMusicDatabase;
    private static Pattern pattern;
    private Matcher matchedEmail;
    private People people;
    private static final String Email_Validate = "^[A-Za-z0-9+_.-]+@(.+)$";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();
        pattern = Pattern.compile(Email_Validate);
        people = (People) getIntent().getSerializableExtra("peopleModel");

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
                    matchedEmail = pattern.matcher(email);

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
                        } else if (!matchedEmail.matches()) {
                            Toast.makeText(RegisterActivity.this, "Email is not valid!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    highMusicDatabase = HighMusicDatabase.getInstance(RegisterActivity.this);
                                    peopleDAO = highMusicDatabase.getPeopleDAO();
                                    Account account = new Account();
                                    int peopleID = (int) peopleDAO.insertPeople(people);
                                    account.setPeopleID(peopleID);
                                    account.setEmail(email);
                                    account.setUsername(username);
                                    account.setPassword(password);
                                    account.setRole(Role.CUSTOMER);
                                    account.setStatus(true);
                                    accountDAO.register(account);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegisterActivity.this, "Register suceessful!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    editor.putInt("id", peopleID);
                                    editor.putString("username", account.getUsername());
                                    editor.putString("role", String.valueOf(account.getRole()));
                                    editor.putString("cartQuantity","0");
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