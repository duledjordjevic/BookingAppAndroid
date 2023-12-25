package com.example.bookingapplication.fragments.addedProperties;

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
import com.example.bookingapplication.databinding.FragmentAccommodationApprovingBinding;
import com.example.bookingapplication.databinding.FragmentAccountAdminBinding;
import com.example.bookingapplication.databinding.FragmentHomeBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.home.HomeViewModel;
import com.example.bookingapplication.model.AccommodationApprovingCard;

import java.util.ArrayList;

public class AccommodationApprovingFragment extends Fragment {

    private AccommodationApprovingViewModel mViewModel;
    private FragmentAccommodationApprovingBinding binding;
    public static ArrayList<AccommodationApprovingCard> cardsApr = new ArrayList<>();
    private AccommodationApprovingViewModel accommodationApprovingViewModel;

    public static AccommodationApprovingFragment newInstance() {
        return new AccommodationApprovingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AccommodationApprovingViewModel homeViewModel =
                new ViewModelProvider(this).get(AccommodationApprovingViewModel.class);

        binding = FragmentAccommodationApprovingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        FragmentTransition.to(ApartmentCardsListFragment.newInstance(products), getActivity() , false, R.id.scroll_products_list);
        FragmentTransition.to(AddedPropertiesFragment.newInstance(cardsApr), getActivity() , false, R.id.scroll_approving_accommodations_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}