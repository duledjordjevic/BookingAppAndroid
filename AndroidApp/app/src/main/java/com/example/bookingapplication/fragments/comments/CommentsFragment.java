package com.example.bookingapplication.fragments.comments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.databinding.FragmentCommentsBinding;

public class CommentsFragment extends Fragment {

    private FragmentCommentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CommentsViewModel commentsViewModel =
                new ViewModelProvider(this).get(CommentsViewModel.class);

        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.commentsTextView;
        commentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}