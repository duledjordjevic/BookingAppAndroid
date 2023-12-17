package com.example.bookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ActivityRegisterBinding;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.Validator;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private TextInputEditText nameInput;
    private TextInputEditText lastNameInput;
    private TextInputEditText phoneInput;
    private TextInputEditText streetInput;
    private TextInputEditText cityInput;
    private TextInputEditText stateInput;
    private TextInputEditText postalCodeInput;
    private AutoCompleteTextView accountAutoCompleteTextView;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText passwordReEnterInput;
    private Button btnSignUp;
    private TextView logInTextView;
    private TextView  emptyInputFields;
    private TextView  wrongEmailFormat;
    private TextView passwordNotMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameInput = binding.nameInput;
        lastNameInput = binding.lastNameInput;
        phoneInput = binding.phoneInput;
        streetInput = binding.streetInput;
        cityInput = binding.cityInput;
        stateInput = binding.stateInput;
        postalCodeInput = binding.postalCodeInput;
        accountAutoCompleteTextView = binding.accountAutoCompleteTextView;
        emailInput = binding.emailInput;
        passwordInput = binding.passwordInput;
        passwordReEnterInput = binding.passwordReEnterInput;
        btnSignUp = binding.btnSignUp;
        logInTextView = binding.logInTextView;
        emptyInputFields = binding.emptyInputFields;
        wrongEmailFormat = binding.emailWrongFormat;
        passwordNotMatch = binding.passwordNotMatch;

        String[] accountTypes = getResources().getStringArray(R.array.account_types);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.account_type_dropdown_item, accountTypes);
        accountAutoCompleteTextView.setAdapter(arrayAdapter);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyInputFields.setText("");
                wrongEmailFormat.setText("");
                passwordNotMatch.setText("");


                String name = nameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                String phoneNumber = phoneInput.getText().toString().trim();
                String street = streetInput.getText().toString().trim();
                String city = cityInput.getText().toString().trim();
                String state = stateInput.getText().toString().trim();
                int postalCode = Integer.parseInt(postalCodeInput.getText().toString().trim());
                String accountType = accountAutoCompleteTextView.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = passwordReEnterInput.getText().toString().trim();

                if(name.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || street.isEmpty() || city.isEmpty()
                    || state.isEmpty() ||  accountType.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    emptyInputFields.setText("All fields must be fill!");
                    return;
                }else if(!Validator.isValidEmail(email)){
                    wrongEmailFormat.setText("Email is in wrong format");
                }else if(!password.equals(confirmPassword)){
                    passwordNotMatch.setText("Passwords not match");
                }else{
                    User user = new User();
                    user.setName(name);
                    user.setLastname(lastName);
                    user.setPhoneNumber(phoneNumber);
                    user.setAddress(new Address(null, street, city, state, postalCode));
                    user.setUserRole(UserType.valueOf(accountType.toUpperCase()));
                    user.setEmail(email);
                    user.setPassword(password);

                    register(user);
                }
            }
        });

        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void register(User user){
        Call<User> call = ClientUtils.authService.register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201){
                    Log.d("REZ","Meesage recieved: ");
                    Toast.makeText(RegisterActivity.this, "Successful. Check your verification email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    };
}