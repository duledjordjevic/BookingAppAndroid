package com.example.bookingapplication.fragments.guestReservations;

import androidx.annotation.Nullable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentGuestReservationsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.model.ReservationGuestCard;

import java.util.ArrayList;

public class GuestReservationsFragment extends Fragment {

    private FragmentGuestReservationsBinding binding;
    public static ArrayList<ReservationGuestCard> cards = new ArrayList<>();

    public static GuestReservationsFragment newInstance() {
        return new GuestReservationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGuestReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(ReservationsGuestCardsListFragment.newInstance(cards), getActivity() , false, R.id.scroll_reservations_guest_cards_list);

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}