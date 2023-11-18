package com.example.bookingapplication.activities.ui.addedProperties;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.activities.ui.account.AccountViewModel;
import com.example.bookingapplication.databinding.FragmentAccountBinding;
import com.example.bookingapplication.databinding.FragmentAddedPropertiesBinding;

public class AddedPropertiesFragment extends Fragment {

    private FragmentAddedPropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddedPropertiesViewModel addedPropertiesViewModel =
                new ViewModelProvider(this).get(AddedPropertiesViewModel.class);

        binding = FragmentAddedPropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.addedPropertiesTextView;
        addedPropertiesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}