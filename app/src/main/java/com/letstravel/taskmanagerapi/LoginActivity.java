package com.letstravel.taskmanagerapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername=findViewById(R.id.etLoginUsername);
        etLoginPassword=findViewById(R.id.etLoginPassword);

        btnLogin=findViewById(R.id.btnLogin);
    }
}
