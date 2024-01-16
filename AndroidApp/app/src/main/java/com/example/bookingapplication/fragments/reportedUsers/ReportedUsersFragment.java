package com.example.bookingapplication.fragments.reportedUsers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentReportedUsersBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.model.UserBlockCard;

import java.util.ArrayList;

public class ReportedUsersFragment extends Fragment {

    private FragmentReportedUsersBinding binding;

    public static ArrayList<UserBlockCard> cards = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReportedUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(ReportedUsersCardsListFragment.newInstance(cards), getActivity() , false, R.id.scroll_user_reports_list);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}