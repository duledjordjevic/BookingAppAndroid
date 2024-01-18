package com.example.bookingapplication.fragments.createAccommodation;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.DateRangeCardAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentCreateAccommodationBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.dateRange.DateRangeCardsListFragment;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.DateRangeCard;
import com.example.bookingapplication.model.enums.AccommodationApprovalStatus;
import com.example.bookingapplication.model.enums.AccommodationType;
import com.example.bookingapplication.model.enums.Amenities;
import com.example.bookingapplication.model.enums.CancellationPolicy;
import com.example.bookingapplication.model.enums.ReservationMethod;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAccommodationFragment extends Fragment {

    private FragmentCreateAccommodationBinding binding;
    private TextInputEditText propertyNameInput;
    private TextInputEditText stateInput;
    private TextInputEditText cityInput;
    private TextInputEditText postalCodeInput;
    private TextInputEditText streetInput;
    private MultiAutoCompleteTextView descriptionInput;
    private AutoCompleteTextView cancellationPolicyTextView;
    private AutoCompleteTextView isPriceForEntireAccTextView;
    private AutoCompleteTextView accommodationTypeTextView;
    private AutoCompleteTextView reservationMethodTextView;
    private TextInputEditText minGuestsInput;
    private TextInputEditText maxGuestsInput;
    private CheckBox wifiCheckBox;
    private CheckBox parkingCheckBox;
    private CheckBox tvCheckBox;
    private CheckBox breakfastCheckBox;
    private CheckBox lunchCheckBox;
    private CheckBox dinnerCheckBox;
    private CheckBox poolCheckBox;
    private CheckBox airConditionCheckBox;
    private CheckBox kitchenCheckBox;
    private Button createAccommodationBtn;
    private TextView emptyInputFields;
    private LinearLayout linearLayoutImages;
    private Button buttonPickImage;
    private List<Uri> imagesAccommodation = new ArrayList<>();
    private TextView imagesMessage;
    private ArrayList<DateRangeCard> dates;
    private Button dateRangeBtn;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    private TextInputEditText priceInput;
    private Button setDateRangeBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCreateAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dates = new ArrayList<>();
        FragmentTransition.to(DateRangeCardsListFragment.newInstance(dates), getActivity() , false, R.id.scroll_date_range_cards_list);

        priceInput = binding.priceInput;
        setDateRangeBtn = binding.setDateRangeBtn;
        dateRangeBtn = binding.dateRangeBtn;
        propertyNameInput = binding.propertyNameInput;
        stateInput = binding.stateInput;
        cityInput = binding.cityInput;
        postalCodeInput = binding.postalCodeInput;
        streetInput = binding.streetInput;
        descriptionInput = binding.descriptionInput;
        cancellationPolicyTextView = binding.cancellationPolicyTextView;
        isPriceForEntireAccTextView = binding.isPriceForEntireAccTextView;
        accommodationTypeTextView = binding.accommodationTypeTextView;
        reservationMethodTextView = binding.reservationMethodTextView;
        minGuestsInput = binding.minGuestsInput;
        maxGuestsInput = binding.maxGuestsInput;
        wifiCheckBox = binding.wifiCheckBox;
        parkingCheckBox = binding.parkingCheckBox;
        tvCheckBox = binding.tvCheckBox;
        breakfastCheckBox = binding.breakfastCheckBox;
        lunchCheckBox = binding.lunchCheckBox;
        dinnerCheckBox = binding.dinnerCheckBox;
        poolCheckBox = binding.poolCheckBox;
        airConditionCheckBox = binding.airConditionCheckBox;
        kitchenCheckBox = binding.kitchenCheckBox;
        createAccommodationBtn = binding.createAccommodationBtn;
        emptyInputFields = binding.emptyInputFields;
        linearLayoutImages = binding.linearlayoutImages;
        buttonPickImage = binding.buttonPickImage;
        imagesMessage = binding.imagesMessage;

        ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(10), uris -> {
                    if (!uris.isEmpty()) {
                        if(uris.size() >= 5){
                            linearLayoutImages.removeAllViews();
                            imagesAccommodation.clear();
                            for(Uri imageUri : uris){
                                imagesAccommodation.add(imageUri);
                                ImageView imageView = new ImageView(getContext());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800,  800);
                                layoutParams.setMargins(5, 10, 5, 5);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imageView.setImageURI(imageUri);

                                linearLayoutImages.addView(imageView);
                            }
                        }else {
                            Toast.makeText(getContext(), "5 images are minimum", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
            }
        });

        dateRangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .setSelection(getDefaultDateSelection())
                        .setCalendarConstraints(getCalendarConstraints())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        String date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
                        String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));
                        selectedStartDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        selectedEndDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        dateRangeBtn.setText(date1 + " - " + date2);
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "tag");

            }
        });
        setDateRangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!priceInput.getText().toString().isEmpty() && selectedStartDate != null && selectedEndDate != null){
                    dates.add(new DateRangeCard(selectedStartDate, selectedEndDate, Double.parseDouble(priceInput.getText().toString())));
                    FragmentTransition.to(DateRangeCardsListFragment.newInstance(dates), getActivity() , false, R.id.scroll_date_range_cards_list);
                    selectedStartDate = null;
                    selectedEndDate = null;
                    priceInput.setText("");
                    dateRangeBtn.setText("Date range");
                }else{
                    Toast.makeText(getActivity(), "Select price and dates!", Toast.LENGTH_LONG).show();
                }
            }
        });


        String[] cancellationPoliciesArray = getResources().getStringArray(R.array.cancellation_policies);
        ArrayAdapter<String> cancellationPolicyAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, cancellationPoliciesArray);
        cancellationPolicyTextView.setAdapter(cancellationPolicyAdapter);

        ArrayAdapter<String> isPriceForEntireAccAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item , new String[]{"yes", "no"});
        isPriceForEntireAccTextView.setAdapter(isPriceForEntireAccAdapter);

        String[] accommodationTypesArray = getResources().getStringArray(R.array.accommodation_types);
        ArrayAdapter<String> accommodationTypesAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, accommodationTypesArray);
        accommodationTypeTextView.setAdapter(accommodationTypesAdapter);

        String[] reservationMethodsArray = getResources().getStringArray(R.array.reservation_methods);
        ArrayAdapter<String> reservationMethodsAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, reservationMethodsArray);
        reservationMethodTextView.setAdapter(reservationMethodsAdapter);

        createAccommodationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyInputFields.setText("");

                String propertyName = propertyNameInput.getText().toString().trim();
                String state = stateInput.getText().toString().trim();
                String city = cityInput.getText().toString().trim();
                String street = streetInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();
                String cancellationPolicy = cancellationPolicyTextView.getText().toString().trim();
                String isPriceForEntireAcc = isPriceForEntireAccTextView.getText().toString().trim();
                String accommodationType = accommodationTypeTextView.getText().toString().trim();
                String reservationMethod= reservationMethodTextView.getText().toString().trim();
                int postalCode = 0;
                if (!postalCodeInput.getText().toString().isEmpty()){
                    postalCode = Integer.parseInt(minGuestsInput.getText().toString().trim());
                }
                int minGuests = 0;
                if (!minGuestsInput.getText().toString().isEmpty()){
                    minGuests = Integer.parseInt(minGuestsInput.getText().toString().trim());
                }
                int maxGuests = 0;
                if (!maxGuestsInput.getText().toString().isEmpty()){
                    maxGuests = Integer.parseInt(maxGuestsInput.getText().toString().trim());
                }

                if (propertyName.isEmpty() || state.isEmpty() || city.isEmpty() || street.isEmpty()
                        || description.isEmpty() || cancellationPolicy.isEmpty() || isPriceForEntireAcc.isEmpty()
                        || reservationMethod.isEmpty() ){
                    emptyInputFields.setText("*All fields must be filled");
                }else if(minGuests < 1 || maxGuests > 10 || minGuests > maxGuests){
                    emptyInputFields.setText("*Max guests is 10. Max guests must be grater or equal than min!");
                }else if(imagesAccommodation.size() < 5){
                    imagesMessage.setText("*Images are required.Min is 5, max is 10.");
                }
                else{
                    Accommodation accommodation = new Accommodation();
                    accommodation.setTitle(propertyName);
                    Address address = new Address(null, street, city, state, postalCode);
                    accommodation.setAddress(address);
                    accommodation.setDescription(description);
                    accommodation.setCancellationPolicy(CancellationPolicy.valueOf(cancellationPolicy));
                    if(isPriceForEntireAcc.equals("yes")){
                        accommodation.setPriceForEntireAcc(true);
                    }else{
                        accommodation.setPriceForEntireAcc(false);
                    }
                    accommodation.setType(AccommodationType.valueOf(accommodationType));
                    accommodation.setReservationMethod(ReservationMethod.valueOf(reservationMethod));
                    accommodation.setMinGuest(minGuests);
                    accommodation.setMaxGuest(maxGuests);
                    List<Amenities> amenities = new ArrayList<>();
                    if(wifiCheckBox.isChecked()) amenities.add(Amenities.WIFI);
                    if(parkingCheckBox.isChecked()) amenities.add(Amenities.PARKING);
                    if(tvCheckBox.isChecked()) amenities.add(Amenities.TV);
                    if(breakfastCheckBox.isChecked()) amenities.add(Amenities.BREAKFAST);
                    if(lunchCheckBox.isChecked()) amenities.add(Amenities.LUNCH);
                    if(dinnerCheckBox.isChecked()) amenities.add(Amenities.DINNER);
                    if(poolCheckBox.isChecked()) amenities.add(Amenities.POOL);
                    if(airConditionCheckBox.isChecked()) amenities.add(Amenities.AIRCONDITION);
                    if(kitchenCheckBox.isChecked()) amenities.add(Amenities.KITCHEN);
                    accommodation.setAmenities(amenities);
                    accommodation.setAccommodationApprovalStatus(AccommodationApprovalStatus.PENDING);
                    accommodation.setHostId(SharedPreferencesManager.getUserInfo(getActivity().getApplicationContext()).getId());
                    createAccommodation(accommodation);
                }
            }
        });


        return root;
    }

    private CalendarConstraints getCalendarConstraints() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long tomorrow = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(tomorrow);
        constraintsBuilder.setValidator(DateValidatorPointForward.from(tomorrow));

        return constraintsBuilder.build();
    }

    private Pair<Long, Long> getDefaultDateSelection() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long tomorrow = calendar.getTimeInMillis();

        return new Pair<>(tomorrow, tomorrow + TimeUnit.DAYS.toMillis(1));
    }

    private void createAccommodation(Accommodation accommodation){
        Call<Accommodation> call = ClientUtils.accommodationService.createAccommodation(accommodation);
        call.enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                if (response.code() == 201){
                    Log.d("REZ","Meesage recieved");
                    Log.d("ID", String.valueOf(response.body().getId()));

                    List<MultipartBody.Part> images = convertImages();
                    postImages(response.body().getId(), images);
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
//                    openDialog();
                }
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Log.d("REZ", "FAIL");
//                serverError.setText("Server error");
            }
        });
    };

    private  List<MultipartBody.Part> convertImages(){
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (Uri imageUri : imagesAccommodation) {
            Log.d("IMAGES", imageUri.getEncodedPath());
            File file = new File(getRealPathFromUri(imageUri));
            if (file.exists()){
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                imageParts.add(part);
            }

        }
        return imageParts;
    }

    public String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    private void postImages(Long accommodationId, List<MultipartBody.Part> images){
        Call<Void> call = ClientUtils.accommodationService.createAccommodationImages(accommodationId, images);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d("IMAGES", "Successful");
                    if(dates != null && dates.size() != 0){
                        postDateRanges(accommodationId);
                    }else{
                        Toast.makeText(getActivity(), "Accommodation successful added", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(getView());
                        navController.navigate(R.id.action_createAccommodationFragment_to_accommodationsForHostFragment);
                    }
                }else{
                    Log.d("IMAGES", "Some other code status");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("IMAGES", t.getMessage() != null?t.getMessage():"error");
                Log.d("IMAGES", "FAIL");
            }
        });
    }

    private void postDateRanges(Long accommodationId){
        Call<Void> call = ClientUtils.accommodationService.addPriceList(accommodationId, dates);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d("PRICELIST", "Successful");
                    Toast.makeText(getActivity(), "Accommodation successful added", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.action_createAccommodationFragment_to_accommodationsForHostFragment);
                }else{
                    Log.d("PRICELIST", "Some other code status");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("IMAGES", t.getMessage() != null?t.getMessage():"error");
                Log.d("IMAGES", "FAIL");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}