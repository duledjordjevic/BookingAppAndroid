package com.example.bookingapplication.activities.ui.reservations;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.activities.ui.account.AccountViewModel;
import com.example.bookingapplication.databinding.FragmentAccountBinding;
import com.example.bookingapplication.databinding.FragmentReservationsBinding;

public class ReservationsFragment extends Fragment {

    private FragmentReservationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReservationsViewModel reservationsViewModel =
                new ViewModelProvider(this).get(ReservationsViewModel.class);

        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.reservationTextView;
        reservationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}