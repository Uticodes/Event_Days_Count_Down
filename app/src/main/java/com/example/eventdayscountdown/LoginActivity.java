package com.example.eventdayscountdown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends AppCompatActivity {
    CheckBox saveLoginCheckbox;
    Boolean saveLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        final EditText userEmail = (EditText) findViewById(R.id.email_login);
        final EditText userPwd = (EditText) findViewById(R.id.pwd_login);
        final TextView newUser = findViewById(R.id.newUser);
        Button loginBtn = (Button) findViewById(R.id.signIn_Btn);
        sharedPreferences = getSharedPreferences("loginRef", MODE_PRIVATE);
        saveLoginCheckbox = (CheckBox) findViewById(R.id.checkBox);
        editor = sharedPreferences.edit();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uEmail = userEmail.getText().toString();
                String uPwd = userPwd.getText().toString();


                //retrieve and compare registration credentials
                SharedPreferences result = getSharedPreferences("DEVFESTPREFS", Context.MODE_PRIVATE);

                String logedUserE = result.getString("useremail", null);
                String logedUserPwd = result.getString("passwd", null);

                if (uEmail.equals(logedUserE) && uPwd.equals(logedUserPwd)) {
                    Toast.makeText(LoginActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    openDashBoard();


                    //save current loginIn details if checkBox is checked
                    if (saveLoginCheckbox.isChecked()) {
                        editor.putBoolean("saveLogin", true);
                        editor.putString("userEmail", uEmail);
                        editor.putString("password", uPwd);
                        editor.commit();
                        openDashBoard();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Unknown User!!! Sign up Instead", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //set the views with previous login details
        saveLogin = sharedPreferences.getBoolean("saveLogin", true);
        if (saveLogin == true) {
            userEmail.setText(sharedPreferences.getString("userEmail", null));
            userPwd.setText(sharedPreferences.getString("password", null));
        }

        //handle signUp
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void openDashBoard() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
