package com.example.bookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ActivityLoginBinding;
import com.example.bookingapplication.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private TextInputEditText nameInput;
    private TextInputEditText lastNameInput;
    private TextInputEditText phoneInput;
    private TextInputEditText addressInput;
    private AutoCompleteTextView accountAutoCompleteTextView;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText passwordReEnterInput;
    private Button btnSignUp;
    private TextView logInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameInput = binding.nameInput;
        lastNameInput = binding.lastNameInput;
        phoneInput = binding.phoneInput;
        addressInput = binding.addressInput;
        accountAutoCompleteTextView = binding.accountAutoCompleteTextView;
        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        passwordReEnterInput = binding.passwordReEnterInput;
        btnSignUp = binding.btnSignUp;
        logInTextView = binding.logInTextView;

        String[] accountTypes = getResources().getStringArray(R.array.account_types);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.account_type_dropdown_item, accountTypes);
        accountAutoCompleteTextView.setAdapter(arrayAdapter);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}