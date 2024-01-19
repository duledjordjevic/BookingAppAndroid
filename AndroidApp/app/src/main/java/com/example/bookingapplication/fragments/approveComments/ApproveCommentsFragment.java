package com.example.bookingapplication.fragments.approveComments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentApproveCommentsBinding;
import com.example.bookingapplication.databinding.FragmentCommentsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;

import java.util.ArrayList;

public class ApproveCommentsFragment extends Fragment {

    private FragmentApproveCommentsBinding binding;
    private Long id;

    public ApproveCommentsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentApproveCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApproveCommentsCardListFragment listFragment = ApproveCommentsCardListFragment.newInstance(new ArrayList<>());
        listFragment.setArguments(getArguments());

        FragmentTransition.to(listFragment, getActivity(), false, R.id.approve_comments_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}