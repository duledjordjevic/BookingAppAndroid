package com.example.bookingapplication.fragments.accountAdmin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAccountAdminBinding;
import com.example.bookingapplication.model.AdminForUpdate;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountAdminFragment extends Fragment {

    private FragmentAccountAdminBinding binding;
    private User adminInfo;
    private User currentUser;
    private AdminForUpdate adminForUpdate;
    private TextView updateProfileValidator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountAdminViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountAdminViewModel.class);

        binding = FragmentAccountAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adminInfo = new User();
        currentUser = SharedPreferencesManager.getUserInfo(this.getContext());
        updateProfileValidator = binding.updateProfileAdminNotValid;

        getAdminInfo();

        Button adminUpdateBtn = binding.btnSaveAccount;
        adminUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileValidator.setText("");
                String newPassword = binding.passwordInputAccount.getText().toString();
                String newPasswordConfirmation = binding.passwordReEnterInputAccount.getText().toString();
                String oldPassword = binding.oldPasswordInput.getText().toString();
                if(oldPassword.equals("")){
                    updateProfileValidator.setText("Password is required");
                    return;
                }
                if(!newPassword.trim().equals(newPasswordConfirmation.trim())){
                    updateProfileValidator.setText("Passwords do not match");
                    return;
                }
                adminForUpdate = new AdminForUpdate(binding.emailInputAccount.getText().toString(),newPassword,oldPassword);
                updateAdminProfile();
            }
        });

//        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void updateAdminProfile(){
        Long id = currentUser.getId();
        Call<User> call = ClientUtils.userService.updateAdmin(id,adminForUpdate);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    Toast.makeText(getContext(), "Succesfully admin updated profile", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Can not update admin profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Can not update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAdminInfo(){
        Long id = SharedPreferencesManager.getUserInfo(this.getContext()).getId();
        Call<User> call = ClientUtils.userService.getAdmin(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    Log.d("Booking","GET BY ID");

                    adminInfo = response.body();
                    binding.emailInputAccount.setText(adminInfo.getEmail());
                    binding.roleInputAcc.setText(String.valueOf(adminInfo.getUserRole()));
                    binding.passwordInputAccount.setText("");
                    binding.passwordReEnterInputAccount.setText("");
                    binding.oldPasswordInput.setText("");

                }else{
                    Log.d("ShopApp","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("ShopApp", t.getMessage() != null?t.getMessage():"error");
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