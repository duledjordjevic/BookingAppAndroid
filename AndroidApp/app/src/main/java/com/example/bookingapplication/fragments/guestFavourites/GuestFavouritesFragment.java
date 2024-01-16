package com.example.bookingapplication.fragments.guestFavourites;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentGuestFavouritesBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.ReservationGuestCard;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestFavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestFavouritesFragment extends Fragment {
    private FragmentGuestFavouritesBinding binding;
    public static ArrayList<ApartmentCard> cards = new ArrayList<>();

    public static GuestFavouritesFragment newInstance() {
        return new GuestFavouritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuestFavouritesBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        FragmentTransition.to(GuestFavouritesCardsListFragment.newInstance(cards), getActivity() , false, R.id.scroll_guest_favourites_list);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}