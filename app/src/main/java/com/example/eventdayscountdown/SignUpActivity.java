package com.example.eventdayscountdown;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class SignUpActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userEmail;
    private EditText userPwd;
    private TextView loginInstead;
    private Button signUp_Btn;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        userName = findViewById(R.id.name_signUp_edt);
        userEmail = findViewById(R.id.email_signup_edt);
        userPwd = findViewById(R.id.password_signup_Edt);
        signUp_Btn = findViewById(R.id.signUp_Btn);
        loginInstead = findViewById(R.id.user_exits);

        signUp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uName = userName.getText().toString().trim();
                String uEmail = userEmail.getText().toString().trim();
                String uPwd = userPwd.getText().toString().trim();

                if (uName.isEmpty()) {
                    userName.setError("Please provide your name");
                    userName.requestFocus();
                } else if (uEmail.isEmpty()) {
                    userEmail.setError("Please provide your email");
                    userEmail.requestFocus();
                } else if (uPwd.isEmpty()) {
                    userPwd.setError("Password cannot be empty");
                    userPwd.requestFocus();
                } else if (!(uName.isEmpty() && uEmail.isEmpty() && uPwd.isEmpty())) {

                    mSharedPreferences = getSharedPreferences("DEVFESTPREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("Value", uName);
                    editor.putString("useremail", uEmail);
                    editor.putString("passwd", uPwd);
                    editor.putString("allInfo", uName + "\n" + uEmail);
                    editor.commit();

                    Toast.makeText(SignUpActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });

        loginInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
