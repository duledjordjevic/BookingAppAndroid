package com.example.bookingapplication.fragments.home;

import static androidx.navigation.Navigation.findNavController;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ApartmentService;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentHomeBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.User;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import retrofit2.Call;

public class HomeFragment extends Fragment {

    public static ArrayList<ApartmentCard> products = new ArrayList<ApartmentCard>();
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareApartmentCardsList(products);

//        homeViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Spinner spinner = binding.btnSort;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sort_arrays));
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = binding.outlinedButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(requireContext()); // Open date picker dialog

            }
        });

        Button buttonEnd = binding.outlinedButton2;
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(requireContext()); // Open date picker dialog

            }
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        FragmentTransition.to(ApartmentCardsListFragment.newInstance(products), getActivity() , false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openDatePicker(Context context){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //Showing the picked value in the textView
//                textView.setText(String.valueOf(year)+ "."+String.valueOf(month)+ "."+String.valueOf(day));

            }
        }, 2023, 01, 20);

        datePickerDialog.show();
    }

    private void prepareApartmentCardsList(ArrayList<ApartmentCard> products){
//        Call<User> call = ApartmentService.
        products.add(new ApartmentCard(1L, "Suncev Breg", "Description 1", "Description 1", R.drawable.apartment_picture));
        products.add(new ApartmentCard(2L, "Suncev Breg", "Description 1", "Description 1", R.drawable.apartment_picture));
        products.add(new ApartmentCard(3L, "Suncev Breg", "Description 1", "Description 1", R.drawable.apartment_picture));
        products.add(new ApartmentCard(4L, "Suncev Breg", "Description 1", "Description 1", R.drawable.apartment_picture));
        products.add(new ApartmentCard(5L, "Suncev Breg", "Description 1", "Description 1", R.drawable.apartment_picture));
    }

}