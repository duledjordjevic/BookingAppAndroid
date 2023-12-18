package com.example.bookingapplication.fragments.hostProperties;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.databinding.FragmentHostPropertiesBinding;

public class HostPropertiesFragment extends Fragment {
    private FragmentHostPropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HostPropertiesViewModel hostPropertiesViewModel =
                new ViewModelProvider(this).get(HostPropertiesViewModel.class);

        binding = FragmentHostPropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.hostPropertiesTextView;
        hostPropertiesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}