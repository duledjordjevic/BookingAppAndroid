package com.example.bookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button btnLogin;
    private TextView joinNowTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        btnLogin = binding.btnLogin;
        joinNowTextView = binding.joinNowTextView;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().equals("admin") && passwordInput.getText().toString().equals("admin")) {
                    Toast.makeText(LoginActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
                }else{
                    openDialog();
                }
            }
        });
        joinNowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openDialog(){
        TryAgainDialog dialog = new TryAgainDialog();
        dialog.show(getSupportFragmentManager(), "try again dialog");
    }
}