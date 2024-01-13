package com.example.bookingapplication.fragments.hostReservations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentGuestReservationsBinding;
import com.example.bookingapplication.databinding.FragmentHostReservationsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.guestReservations.GuestReservationsFragment;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.ReservationHostCard;

import java.util.ArrayList;

public class HostReservationsFragment extends Fragment {

    private FragmentHostReservationsBinding binding;
    public static ArrayList<ReservationHostCard> cards = new ArrayList();

    public static GuestReservationsFragment newInstance() {
        return new GuestReservationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHostReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(ReservationsHostCardsListFragment.newInstance(cards), getActivity() , false, R.id.scroll_reservations_host_cards_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}