package com.example.bookingapplication.fragments.comments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentCommentsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;

import java.util.ArrayList;

public class CommentsFragment extends Fragment {

    private FragmentCommentsBinding binding;
    private Long id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CommentCardListFragment listFragment = CommentCardListFragment.newInstance(new ArrayList<>());
        listFragment.setArguments(getArguments());

        FragmentTransition.to(listFragment, getActivity(), false, R.id.scroll_comments_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}