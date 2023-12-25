package com.example.bookingapplication.fragments.apartmentDetails;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentDetailsBinding;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.enums.Amenities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentDetailsFragment extends Fragment {

    private ApartmentDetailsViewModel mViewModel;
    private FragmentApartmentDetailsBinding binding;

    private TextView title;
    private TextView description;
    private TextView rating;
    private TextView address;
    private TextView amenities;
    private Accommodation accommodation;
    private ImageView imageView;
    private Long id;

    public static ApartmentDetailsFragment newInstance() {
        return new ApartmentDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apartment_details, container, false);
        binding = FragmentApartmentDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        binding = FragmentApartmentDetailsBinding.inflate(inflater, container, false);
        Button button = binding.button1;
        title = binding.title;
        description = binding.description;
        rating = binding.rating;
        address = binding.address;
        amenities = binding.amenities;
        imageView = binding.imageView;

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("apartmentId");
            Log.e("Id", String.valueOf(id));
            // Koristite apartmentId kako je potrebno
        }
        Log.e("Id", String.valueOf(id));
        setValues();
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


        return root;
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

    private void setValues(){
        Call<Accommodation> call = ClientUtils.accommodationService.getAccommodationDetails(id);
        call.enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                Log.d("Responseeeee", String.valueOf(response.code()));
                accommodation = response.body();
                title.setText(accommodation.getTitle());
                address.setText(accommodation.getAddress().toString());
                description.setText(accommodation.getDescription());
                amenities.setText(mapAmenitieToString(accommodation.getAmenities()));
                rating.setText("Rating: ");
                imageView.setImageBitmap(convertBase64ToBitmap(accommodation.getImages().get(0)));
//                Log.d("Accommodation", response.body().toString());
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });


    }

    private String mapAmenitieToString(List<Amenities> amenities){
        String result = "";
        for (Amenities amenitie: amenities
             ) {
                result += "- " + amenitie.toString() + "\n";
        }
        return result;
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}