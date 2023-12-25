package com.example.bookingapplication.fragments.addedProperties;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.databinding.FragmentAddedPropertiesBinding;

public class AddedPropertiesFragment extends Fragment {

    private FragmentAddedPropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddedPropertiesViewModel addedPropertiesViewModel =
                new ViewModelProvider(this).get(AddedPropertiesViewModel.class);

        binding = FragmentAddedPropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}