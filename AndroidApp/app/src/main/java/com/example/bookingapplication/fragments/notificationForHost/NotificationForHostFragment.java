package com.example.bookingapplication.fragments.notificationForHost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentNotificationForHostBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.model.NotificationForHost;

import java.util.ArrayList;

public class NotificationForHostFragment extends Fragment {

    public static ArrayList<NotificationForHost> products = new ArrayList<NotificationForHost>();
    FragmentNotificationForHostBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationForHostBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        FragmentTransition.to(NotificationForHostCardListFragment.newInstance(products), getActivity() , false, R.id.scroll_notification_for_host_list);


        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}