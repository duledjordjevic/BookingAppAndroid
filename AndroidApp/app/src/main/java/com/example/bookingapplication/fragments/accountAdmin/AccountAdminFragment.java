package com.example.bookingapplication.fragments.accountAdmin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentAccountAdminBinding;
import com.example.bookingapplication.databinding.FragmentAccountBinding;
import com.example.bookingapplication.fragments.account.AccountViewModel;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountAdminFragment extends Fragment {

    private FragmentAccountAdminBinding binding;
    private User userUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountAdminViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountAdminViewModel.class);

        binding = FragmentAccountAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userUpdate = new User();

        getUserInfo();
        Button adminUpdateBtn = binding.btnSaveAccount;
        adminUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdminProfile();
            }
        });

//        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void updateAdminProfile(){

    }
    private void getUserInfo(){
        Long id = SharedPreferencesManager.getUserInfo(this.getContext()).getId();
        Call<User> call = ClientUtils.updateUserService.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    Log.d("Booking","GET BY ID");

                    userUpdate = response.body();
                    binding.emailInputAccount.setText(userUpdate.getEmail());
                    binding.roleInputAcc.setText(String.valueOf(userUpdate.getUserRole()));

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