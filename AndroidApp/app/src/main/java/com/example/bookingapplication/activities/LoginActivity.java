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
    private TextView emptyInputFields;
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
        emptyInputFields = binding.emptyInputFields;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().equals("admin") && passwordInput.getText().toString().equals("1")) {
                    Toast.makeText(LoginActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("Uloga","Admin");
                    startActivity(intent);
                    finish();
                }else if(emailInput.getText().toString().equals("guest") && passwordInput.getText().toString().equals("1")){
                    Toast.makeText(LoginActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("Uloga","Guest");
                    startActivity(intent);
                    finish();
                }else if(emailInput.getText().toString().equals("host") && passwordInput.getText().toString().equals("1")){
                    Toast.makeText(LoginActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("Uloga","Host");
                    startActivity(intent);
                    finish();
                }else if(emailInput.getText().toString().equals("") || passwordInput.getText().toString().equals("")){
                    emptyInputFields.setText("All fields must be fill!");
                }else{
                    openDialog();
                    emptyInputFields.setText("");
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