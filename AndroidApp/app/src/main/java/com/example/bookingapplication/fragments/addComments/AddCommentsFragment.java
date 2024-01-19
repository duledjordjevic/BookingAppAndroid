package com.example.bookingapplication.fragments.addComments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentAddCommentsBinding;
import com.example.bookingapplication.databinding.FragmentMyCommentsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.myComments.MyCommentsCardListFragment;

import java.util.ArrayList;

public class AddCommentsFragment extends Fragment {

    private FragmentAddCommentsBinding binding;
    private Long id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AddCommentsCardListFragment listFragment = AddCommentsCardListFragment.newInstance(new ArrayList<>());
        listFragment.setArguments(getArguments());

        FragmentTransition.to(listFragment, getActivity(), false, R.id.scroll_addComments_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}