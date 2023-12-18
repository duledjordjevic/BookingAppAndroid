package com.example.bookingapplication.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapplication.databinding.FragmentAccountBinding;
import com.google.android.material.textfield.TextInputEditText;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.nameInputAccount.setText("Dusan");
        binding.lastNameInputAccount.setText("Djordjevic");
        binding.phoneInputAccount.setText("064123123");
        binding.streetInput.setText("Banovic Strahinje 10");
        binding.emailInputAccount.setText("ftn@uns.ac.rs");
        binding.passwordInputAccount.setText("nekasifra123");
        binding.passwordReEnterInputAccount.setText("nekasifra123");

//        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}