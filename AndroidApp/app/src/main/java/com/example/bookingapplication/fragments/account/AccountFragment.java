package com.example.bookingapplication.fragments.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapplication.activities.LoginActivity;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAccountBinding;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.UserDelete;
import com.example.bookingapplication.model.UserForUpdate;
import com.example.bookingapplication.util.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private User userInfo;
    private User currentUser;
    private UserForUpdate userForUpdate;
    private UserDelete userDelete;
    private TextView updateProfileValidator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userInfo = new User();
        currentUser = SharedPreferencesManager.getUserInfo(this.getContext().getApplicationContext());
        updateProfileValidator = binding.updateProfileNotValid;

        getUserInfo();
        Button userUpdateBtn = binding.btnSaveAccount;
        Button userDeleteBtn = binding.btnDeleteAccount;

        userUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileValidator.setText("");
                String street = binding.streetInput.getText().toString();
                String city = binding.cityInput.getText().toString();
                String state = binding.stateInput.getText().toString();
                String postalCode = binding.postalCodeInput.getText().toString();
                String email = binding.emailInputAccount.getText().toString();
                String phoneNumber = binding.phoneInputAccount.getText().toString();
                String name = binding.nameInputAccount.getText().toString();
                String lastname = binding.lastNameInputAccount.getText().toString();
                String newPassword = binding.passwordInputAccount.getText().toString();
                String newPasswordConfirmation = binding.passwordReEnterInputAccount.getText().toString();
                String oldPassword = binding.oldPasswordInput.getText().toString();
                if(oldPassword.equals("")){
                    updateProfileValidator.setText("Password is required");
                    return;
                }
                if(street.equals("") || city.equals("") || state.equals("") || postalCode.equals("") || email.equals("") || phoneNumber.equals("") ||
                name.equals("") || lastname.equals("") ){
                    updateProfileValidator.setText("All fields must be fill");
                    return;
                }
                if(!newPassword.trim().equals(newPasswordConfirmation.trim())){
                    updateProfileValidator.setText("Passwords do not match");
                    return;
                }
                Address address = new Address(null,street,city,state,Integer.valueOf(postalCode));
                userForUpdate = new UserForUpdate(email,address,phoneNumber,name,lastname,oldPassword,newPassword);

                updateUserProfile();
            }
        });

        userDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileValidator.setText("");
                String password = binding.oldPasswordInput.getText().toString();
                if(password.equals("")){
                    updateProfileValidator.setText("Password is required");
                    return;
                }
                userDelete = new UserDelete(password);
                deleteUserProfile();
            }
        });

//        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void updateUserProfile(){
        Long id = currentUser.getId();
        Call<User> call = ClientUtils.userService.updateProfile(id,userForUpdate);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Succesfully updated profile", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Can not update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Can not update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteUserProfile(){
        Long id = currentUser.getId();
        Call<Boolean> call = ClientUtils.userService.deleteProfile(userDelete,id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    SharedPreferencesManager.clearUserInfo(getActivity().getApplicationContext());
                    startActivity(intent);
                    Toast.makeText(getContext(), "Succesfully deleted profile", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Can not delete profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
    private void getUserInfo(){
        Long id = currentUser.getId();
        Call<User> call = ClientUtils.userService.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("Response","GET BY ID" + response.code());
                if (response.code() == 200){
                    Log.d("Booking","GET BY ID");

                    userInfo = response.body();
                    binding.nameInputAccount.setText(userInfo.getName());
                    binding.lastNameInputAccount.setText(userInfo.getLastname());
                    binding.phoneInputAccount.setText(userInfo.getPhoneNumber());
                    binding.streetInput.setText(userInfo.getAddress().getStreet());
                    binding.cityInput.setText(userInfo.getAddress().getCity());
                    binding.stateInput.setText(userInfo.getAddress().getState());
                    binding.postalCodeInput.setText(String.valueOf(userInfo.getAddress().getPostalCode()));
                    binding.emailInputAccount.setText(userInfo.getEmail());
                    binding.roleInputAcc.setText(String.valueOf(userInfo.getUserRole()));
                    binding.passwordInputAccount.setText("");
                    binding.passwordReEnterInputAccount.setText("");
                    binding.oldPasswordInput.setText("");

                }else{
                    Log.d("ShopApp","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("BOOKING", t.getMessage() != null?t.getMessage():"error");
            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}