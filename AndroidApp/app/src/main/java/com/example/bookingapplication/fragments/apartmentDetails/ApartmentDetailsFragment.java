package com.example.bookingapplication.fragments.apartmentDetails;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.FragmentApartmentDetailsBinding;

public class ApartmentDetailsFragment extends Fragment {

    private ApartmentDetailsViewModel mViewModel;
    private FragmentApartmentDetailsBinding binding;

    public static ApartmentDetailsFragment newInstance() {
        return new ApartmentDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apartment_details, container, false);
        binding = FragmentApartmentDetailsBinding.inflate(inflater, container, false);
        Button button = binding.button1;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(requireContext()); // Open date picker dialog

            }
        });

        Button buttonEnd = binding.button2;
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePicker(requireContext()); // Open date picker dialog

            }
        });


        return inflater.inflate(R.layout.fragment_apartment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ApartmentDetailsViewModel.class);
        // TODO: Use the ViewModel
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


}