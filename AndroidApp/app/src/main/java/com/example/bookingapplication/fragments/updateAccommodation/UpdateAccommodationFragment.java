package com.example.bookingapplication.fragments.updateAccommodation;

import static androidx.navigation.Navigation.findNavController;

import static com.example.bookingapplication.fragments.createAccommodation.CreateAccommodationFragment.getCalendarConstraints;
import static com.example.bookingapplication.fragments.createAccommodation.CreateAccommodationFragment.getDefaultDateSelection;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
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
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentCreateAccommodationBinding;
import com.example.bookingapplication.databinding.FragmentUpdateAccommodationBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.dateRange.DateRangeCardsListFragment;
import com.example.bookingapplication.helpers.ImageHelper;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.DateRangeCard;
import com.example.bookingapplication.model.enums.AccommodationApprovalStatus;
import com.example.bookingapplication.model.enums.AccommodationType;
import com.example.bookingapplication.model.enums.Amenities;
import com.example.bookingapplication.model.enums.CancellationPolicy;
import com.example.bookingapplication.model.enums.ReservationMethod;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccommodationFragment extends Fragment {

    private FragmentUpdateAccommodationBinding binding;
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
    private Accommodation accommodation;
    private Long id;
    private List<String> existingImagesAccommodation = new ArrayList<>();
    private List<Uri> existingImagesUri = new ArrayList<>();
    private ArrayList<DateRangeCard> dates;
    private Button dateRangeBtn;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    private TextInputEditText priceInput;
    private Button setDateRangeBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentUpdateAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        dates = new ArrayList<>();
//        FragmentTransition.to(DateRangeCardsListFragment.newInstance(dates), getActivity() , false, R.id.scroll_date_range_cards_list);



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

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("apartmentId");
            Log.e("Id", String.valueOf(id));
            // Koristite apartmentId kako je potrebno
        }
        Log.e("Id", String.valueOf(id));
        setDateIntervals();
        setValues();

        ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
                registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(10), uris -> {
                    if (!uris.isEmpty()) {
                            for(Uri imageUri : uris){
                                imagesAccommodation.add(imageUri);
                                ImageView imageView = new ImageView(getContext());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800,  800);
                                layoutParams.setMargins(5, 10, 5, 5);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imageView.setImageURI(imageUri);

                                linearLayoutImages.addView(imageView);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        imagesAccommodation.remove(imageUri);
                                        linearLayoutImages.removeView(imageView);
                                    }
                                });
                            }

                    } else {
                        Log.d("PhotoPicker", "No media selected");
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
        buttonPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
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
                }else if((existingImagesAccommodation.size() + imagesAccommodation.size()) < 5){
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
                    accommodation.setHostId(SharedPreferencesManager.getUserInfo(getActivity()).getId());

                    updateAccommodation(accommodation);
                }
            }
        });


        return root;
    }
    private void setValues(){
        Call<Accommodation> call = ClientUtils.accommodationService.getAccommodationDetails(id);
        call.enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                Log.d("Responseeeee", String.valueOf(response.code()));
                accommodation = response.body();
                Log.d("Responseeeee", String.valueOf(accommodation.getId()));
                propertyNameInput.setText(accommodation.getTitle());
                stateInput.setText(accommodation.getAddress().getState());
                cityInput.setText(accommodation.getAddress().getCity());
                postalCodeInput.setText(String.valueOf(accommodation.getAddress().getPostalCode()));
                streetInput.setText(accommodation.getAddress().getStreet());
                descriptionInput.setText(accommodation.getDescription());

                minGuestsInput.setText(String.valueOf(accommodation.getMinGuest()));
                maxGuestsInput.setText(String.valueOf(accommodation.getMaxGuest()));

                cancellationPolicyTextView.setText(String.valueOf(accommodation.getCancellationPolicy()),false);

                if(accommodation.isPriceForEntireAcc()){
                    isPriceForEntireAccTextView.setText("yes",false);
                }else{
                    isPriceForEntireAccTextView.setText("no",false);
                }
                accommodationTypeTextView.setText(String.valueOf(accommodation.getType()),false);
                reservationMethodTextView.setText(String.valueOf(accommodation.getReservationMethod()),false);

                if(accommodation.getAmenities().contains(Amenities.TV)){
                    tvCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.DINNER)){
                    dinnerCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.LUNCH)){
                    lunchCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.BREAKFAST)){
                    breakfastCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.WIFI)){
                    wifiCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.POOL)){
                    poolCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.AIRCONDITION)){
                    airConditionCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.KITCHEN)){
                    kitchenCheckBox.setChecked(true);
                }
                if(accommodation.getAmenities().contains(Amenities.PARKING)){
                    parkingCheckBox.setChecked(true);
                }
                for(String image: accommodation.getImages()){
                    existingImagesAccommodation.add(image);
                    Bitmap imageBitMap = convertBase64ToBitmap(image);
                    ImageView imageView = new ImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800,  800);
                    layoutParams.setMargins(5, 10, 5, 5);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    imageView.setImageBitmap(imageBitMap);

                    linearLayoutImages.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            existingImagesAccommodation.remove(image);
                            linearLayoutImages.removeView(imageView);
                        }
                    });
                }


//                amenities.setText(mapAmenitieToString(accommodation.getAmenities()));
//                rating.setText("Rating: ");
//                imageView.setImageBitmap(convertBase64ToBitmap(accommodation.getImages().get(0)));
//                Log.d("Accommodation", response.body().toString());
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });


    }
    private void setDateIntervals(){
        Call<List<DateRangeCard>> call = ClientUtils.accommodationService.getDateIntervals(id);
        call.enqueue(new Callback<List<DateRangeCard>>() {
            @Override
            public void onResponse(Call<List<DateRangeCard>> call, Response<List<DateRangeCard>> response) {
                dates = new ArrayList<>(response.body());
                FragmentTransition.to(DateRangeCardsListFragment.newInstance(dates), getActivity() , false, R.id.scroll_date_range_cards_list);
            }

            @Override
            public void onFailure(Call<List<DateRangeCard>> call, Throwable t) {

            }
        });
    }

    private void updateAccommodation(Accommodation accommodation){
        Call<Accommodation> call = ClientUtils.accommodationService.updateAccommodation(accommodation,id);
        call.enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                if (response.code() == 201){
                    Log.d("REZ","Meesage recieved");
                    Log.d("ID", String.valueOf(id));

                    List<MultipartBody.Part> images = convertImages();
                    postImages(id, images);
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
        convertStringToUri();
        imagesAccommodation.addAll(existingImagesUri);
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
    private void postDateRanges(Long accommodationId){
        Call<Void> call = ClientUtils.accommodationService.addPriceList(accommodationId, dates);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d("PRICELIST", "Successful");
                    Toast.makeText(getActivity(), "Accommodation successful added", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.action_updateAccommodationFragment_to_accommodationsForHostFragment);
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

    private void postImages(Long accommodationId, List<MultipartBody.Part> images){
        Call<Void> call = ClientUtils.accommodationService.createAccommodationImages(accommodationId, images);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    deleteAddedImages();
                    if(dates != null && dates.size() != 0){
                        postDateRanges(accommodationId);
                    }else{
                        Toast.makeText(getActivity(), "Accommodation successful added", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(getView());
                        navController.navigate(R.id.action_updateAccommodationFragment_to_accommodationsForHostFragment);
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
    private void deleteAddedImages() {
        ContentResolver resolver = getContext().getContentResolver();
        for (Uri image : existingImagesUri) {
            resolver.delete(image, null, null);
        }
    }
    private void convertStringToUri(){
        existingImagesUri = new ArrayList<>();
        for(String imageString: existingImagesAccommodation){
            existingImagesUri.add(getImageUri(getContext(),convertBase64ToBitmap(imageString)));
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}