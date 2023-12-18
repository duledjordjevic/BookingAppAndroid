package com.example.bookingapplication.fragments.reportedUsers;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.databinding.FragmentReportedUsersBinding;

public class ReportedUsersFragment extends Fragment {

    private FragmentReportedUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReportedUsersViewModel reportedUsersViewModel =
                new ViewModelProvider(this).get(ReportedUsersViewModel.class);

        binding = FragmentReportedUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.reportedUsersTextView;
        reportedUsersViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}