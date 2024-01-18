package com.example.bookingapplication.fragments.notificationForGuest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentNotificationsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.notificationForHost.NotificationForHostCardListFragment;
import com.example.bookingapplication.model.NotificationForGuest;
import com.example.bookingapplication.model.NotificationForHost;

import java.util.ArrayList;

public class NotificationForGuestFragment extends Fragment {

    public static ArrayList<NotificationForGuest> products = new ArrayList<NotificationForGuest>();
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(NotificationForGuestCardListFragment.newInstance(products), getActivity() , false, R.id.scroll_notification_for_guest_list);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}