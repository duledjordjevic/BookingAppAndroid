package com.example.bookingapplication.fragments.accommodationsForHost;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentAccommodationsForHostBinding;
import com.example.bookingapplication.databinding.FragmentHomeBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.home.ApartmentCardsListFragment;
import com.example.bookingapplication.fragments.home.HomeViewModel;
import com.example.bookingapplication.model.ApartmentCard;

import java.util.ArrayList;

public class AccommodationsForHostFragment extends Fragment {

    public static ArrayList<ApartmentCard> products = new ArrayList<ApartmentCard>();
    private AccommodationsForHostViewModel accommodationsForHostViewModel;
    private FragmentAccommodationsForHostBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccommodationsForHostViewModel homeViewModel =
                new ViewModelProvider(this).get(AccommodationsForHostViewModel.class);

        binding = FragmentAccommodationsForHostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransition.to(AccForHostListFragment.newInstance(products), getActivity() , false, R.id.scroll_accommodations_for_host_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}