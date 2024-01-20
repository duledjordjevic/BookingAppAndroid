package com.example.bookingapplication.fragments.home;

import static androidx.navigation.Navigation.findNavController;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.bookingapplication.fragments.addedProperties.AddedPropertiesFragment;
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.example.bookingapplication.model.AccommodationCard;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.AccommodationType;
import com.example.bookingapplication.model.enums.Amenities;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static ArrayList<ApartmentCard> products = new ArrayList<ApartmentCard>();
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextInputEditText searchInput;
    private Button startDateBtn;
    private Button endDateBtn;
    private Button searchBtn;
    private Button filterBtn;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isWifiChecked;
    private boolean isAirConditionChecked;
    private boolean isParkingChecked;
    private boolean isPoolChecked;
    private boolean isBreakfastChecked;
    private boolean isLunchChecked;
    private boolean isDinnerChecked;
    private boolean isKitchenChecked;
    private boolean isTvChecked;
    private String selectedAccommodationType;
    private Set<String> selectedAmenities = new HashSet<>();
    private Map<String, String> map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        map = new HashMap<>();
        map.put("city", "");
        Log.d("login1" , SharedPreferencesManager.getUserInfo(getActivity().getApplicationContext()).getJwt() + " "  + SharedPreferencesManager.getUserInfo(getActivity().getApplicationContext()).getId());

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        searchInput = binding.searchInput;
        startDateBtn = binding.startDateBtn;
        endDateBtn = binding.endDateBtn;
        searchBtn = binding.searchBtn;
        filterBtn = binding.filterButton;

//        homeViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sort_arrays));

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("startDate");
            }
        });
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("endDate");
            }
        });
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ShopApp", "Bottom Sheet Dialog");
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
//                View dialogView = getLayoutInflater().inflate(R.layout.filter, null);
//                bottomSheetDialog.setContentView(dialogView);
//                bottomSheetDialog.show();
                openFilterDialog();
                Log.d("SelectedAmenities", selectedAmenities.toString());
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = new HashMap<>();
                if(startDate != null && endDate != null){
                    map.put("startDate", String.valueOf(startDate));
                    map.put("endDate", String.valueOf(endDate));
                }
                if(!mapToAmenitiesList(selectedAmenities).isEmpty()){
                    map.put("amenities", formatAmenitiesList(mapToAmenitiesList(selectedAmenities)));
                }
                if(selectedAccommodationType != null){
                    AccommodationType type = AccommodationType.valueOf(selectedAccommodationType.toUpperCase());
                    map.put("accommodationType", String.valueOf(type));
                }
                map.put("city", "");
                if(!searchInput.getText().toString().isEmpty()){
                    map.put("city", searchInput.getText().toString());
                }
                FragmentTransition.to(ApartmentCardsListFragment.newInstance(products, map), getActivity() , false, R.id.scroll_products_list);

//                Call<List<Card>> call = ClientUtils.accommodationService.filterAccommodations(map);
//                call.enqueue(new Callback<List<Card>>() {
//                    @Override
//                    public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
//                        Log.d("filterRezultat", response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Card>> call, Throwable t) {
//                        Log.d("Fail", t.toString());
//                    }
//                });
            }

        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        FragmentTransition.to(ApartmentCardsListFragment.newInstance(products, map), getActivity() , false, R.id.scroll_products_list);

        return root;
    }

    private void openFilterDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.filter, null);
        bottomSheetDialog.setContentView(dialogView);

        RadioGroup accommodationTypeRadioGroup = dialogView.findViewById(R.id.accommodationTypeRadioGroup);

        // Dodajte osluškivač za promenu selektovanog RadioButton-a
        accommodationTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = dialogView.findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedAccommodationType = selectedRadioButton.getText().toString();
                Log.d("SelectedAccommodationType", selectedAccommodationType);
            }
        });

        // Pronađite sve CheckBox-e
        CheckBox wifiCheckBox = dialogView.findViewById(R.id.wifiCheckBox);
        CheckBox airConditionCheckBox = dialogView.findViewById(R.id.airConditionCheckBox);
        CheckBox parkingCheckBox = dialogView.findViewById(R.id.parkingCheckBox);
        CheckBox poolCheckBox = dialogView.findViewById(R.id.poolCheckBox);
        CheckBox breakfastCheckBox = dialogView.findViewById(R.id.breakfastCheckBox);
        CheckBox lunchCheckBox = dialogView.findViewById(R.id.lunchCheckBox);
        CheckBox dinnerCheckBox = dialogView.findViewById(R.id.dinnerCheckBox);
        CheckBox kitchenCheckBox = dialogView.findViewById(R.id.kitchenCheckBox);
        CheckBox tvCheckBox = dialogView.findViewById(R.id.tvCheckBox);

        // Postavite stanje CheckBox-eva na osnovu prethodno selektovanih amenitija
        wifiCheckBox.setChecked(selectedAmenities.contains(getString(R.string.wifi)));
        airConditionCheckBox.setChecked(selectedAmenities.contains(getString(R.string.air_condition)));
        parkingCheckBox.setChecked(selectedAmenities.contains(getString(R.string.parking)));
        poolCheckBox.setChecked(selectedAmenities.contains(getString(R.string.pool)));
        breakfastCheckBox.setChecked(selectedAmenities.contains(getString(R.string.breakfast)));
        lunchCheckBox.setChecked(selectedAmenities.contains(getString(R.string.lunch)));
        dinnerCheckBox.setChecked(selectedAmenities.contains(getString(R.string.dinner)));
        kitchenCheckBox.setChecked(selectedAmenities.contains(getString(R.string.kitchen)));
        tvCheckBox.setChecked(selectedAmenities.contains(getString(R.string.tv)));

        // Dodajte osluškivače za svaki CheckBox
        wifiCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.wifi)));
        airConditionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.air_condition)));
        parkingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.parking)));
        poolCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.pool)));
        breakfastCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.breakfast)));
        lunchCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.lunch)));
        dinnerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.dinner)));
        kitchenCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.kitchen)));
        tvCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSelectedAmenities(isChecked, getString(R.string.tv)));

        // Dodajte osluškivač za pozadinski View
        View backgroundView = dialogView.findViewById(R.id.dialogBackgroundView);
        backgroundView.setOnClickListener(v -> {
            // Zatvorite dijalog kada korisnik stisne izvan njega
            bottomSheetDialog.dismiss();

            // Ispisivanje selektovanih amenitija i tipa smeštaja
            Log.d("SelectedAmenities", selectedAmenities.toString());
            if (selectedAccommodationType != null) {
                Log.d("SelectedAccommodationType", selectedAccommodationType);
            }
        });

        bottomSheetDialog.show();
    }

    // Metoda koja ažurira izabrane amenitije u skladu sa promenom stanja CheckBox-a
    private void updateSelectedAmenities(boolean isChecked, String amenity) {
        if (isChecked) {
            selectedAmenities.add(amenity);
        } else {
            selectedAmenities.remove(amenity);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDatePickerDialog(String datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        if (datePicker.equals("startDate")){
                            startDateBtn.setText(selectedDate);
                            startDate = LocalDate.of(year, month + 1, dayOfMonth);
                        }else{
                            endDateBtn.setText(selectedDate);
                            endDate = LocalDate.of(year, month + 1, dayOfMonth);
                        }

                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    public static List<Amenities> mapToAmenitiesList(Set<String> amenitiesStrings) {
        List<Amenities> amenitiesList = new ArrayList<>();

        for (String amenityString : amenitiesStrings) {
            try {
                Amenities amenity = Amenities.valueOf(amenityString.toUpperCase());
                amenitiesList.add(amenity);
            } catch (IllegalArgumentException e) {

            }
        }

        return amenitiesList;
    }

    public String formatAmenitiesList(List<Amenities> amenitiesList) {
        StringBuilder result = new StringBuilder();

        for (Amenities amenity : amenitiesList) {
            result.append(amenity.name()).append(", ");
        }

        // Ukloni poslednji zarez i razmak
        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }

}