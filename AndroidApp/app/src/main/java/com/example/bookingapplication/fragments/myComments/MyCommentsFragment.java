package com.example.bookingapplication.fragments.myComments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentCommentsBinding;
import com.example.bookingapplication.databinding.FragmentMyCommentsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;

import java.util.ArrayList;

public class MyCommentsFragment extends Fragment {

    private FragmentMyCommentsBinding binding;
    private Long id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MyCommentsCardListFragment listFragment = MyCommentsCardListFragment.newInstance(new ArrayList<>());
        listFragment.setArguments(getArguments());

        FragmentTransition.to(listFragment, getActivity(), false, R.id.scroll_myComments_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}