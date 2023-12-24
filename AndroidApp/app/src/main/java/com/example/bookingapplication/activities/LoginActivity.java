package com.example.bookingapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ActivityLoginBinding;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.example.bookingapplication.util.Validator;
import com.google.android.material.textfield.TextInputEditText;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import java.text.ParseException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button btnLogin;
    private TextView joinNowTextView;
    private TextView emptyInputFields;
    private TextView emailWrong;
    private TextView serverError;
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
        emailWrong = binding.emailWrong;
        serverError = binding.serverError;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyInputFields.setText("");
                emailWrong.setText("");
                serverError.setText("");

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    emptyInputFields.setText("All fields must be fill!");
                }else if(!Validator.isValidEmail(email)){
                    emailWrong.setText("Email is in wrong format");
                }else{
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    postLogin(user);
                }
            }
        });
        joinNowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void postLogin(User user){
        Call<User> call = ClientUtils.authService.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    Log.d("REZ",response.body().getJwt());

                    JWT jwt = null;
                    JWTClaimsSet claimsSet = null;
                    Integer id = null;
                    String role = "";
                    try {
                        jwt = JWTParser.parse(response.body().getJwt());
                        claimsSet = jwt.getJWTClaimsSet();
                        id = claimsSet.getIntegerClaim("id");
                        role = claimsSet.getStringListClaim("Authorities").get(0);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    SharedPreferencesManager.saveUserInfo(getApplicationContext(), response.body().getEmail(), UserType.valueOf(role), id, response.body().getJwt());

                    Toast.makeText(LoginActivity.this, "Successful log in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("Role", role);
                    startActivity(intent);
                    finish();
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                    openDialog();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                serverError.setText("Server error");
            }
        });
    };


    public void openDialog(){
        TryAgainDialog dialog = new TryAgainDialog();
        dialog.show(getSupportFragmentManager(), "try again dialog");
    }
}