package com.example.bookingapplication.fragments.apartmentDetails;

import static androidx.navigation.Navigation.findNavController;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentApartmentDetailsBinding;
import com.example.bookingapplication.dialog.CommentReportDialog;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Address;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.CreateReservation;
import com.example.bookingapplication.model.enums.Amenities;
import com.example.bookingapplication.model.enums.ReservationMethod;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private Button reserveButton;
    private AutoCompleteTextView guestsTextView;
    private  ArrayList<String> numberOfguests;
    private ArrayList<Date> avaliableDates;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    private Integer numOfguests;
    private TextView priceText;

    private LinearLayout dateRangePrice;
    private LinearLayout optionsReserve;
    private Double accommodationRating;
    private Double hostRating;
    private TextView ratingAcc;
    private TextView ratingHost;

    public static ApartmentDetailsFragment newInstance() {
        return new ApartmentDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apartment_details, container, false);
        binding = FragmentApartmentDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button dateRangeBtn = binding.dateRangeBtn;
        reserveButton = binding.reserveButton;
        title = binding.title;
        description = binding.description;
        ratingAcc = binding.ratingAcc;
        ratingHost = binding.ratingHost;
        address = binding.address;
        amenities = binding.amenities;
        imageView = binding.imageView;
        guestsTextView = binding.guestsTextView;
        priceText = binding.priceText;
        dateRangePrice = binding.dateRangePrice;
        optionsReserve = binding.optionsReserve;


        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("apartmentId");
            Log.e("Id", String.valueOf(id));

        }

        Log.e("Id", String.valueOf(id));
        setValues();

        UserType role = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getUserRole();
        switch (role) {
            case GUEST:
                break;
            case ADMIN:
            case HOST:
                for (int i = 0; i < dateRangePrice.getChildCount(); i++) {
                    View child = dateRangePrice.getChildAt(i);
                    child.setVisibility(View.GONE);
                }
                for (int i = 0; i < optionsReserve.getChildCount(); i++) {
                    View child = optionsReserve.getChildAt(i);
                    child.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
        dateRangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .setSelection(getDefaultDateSelection())
                        .setCalendarConstraints(getCalendarConstraints(avaliableDates))
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        String date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.first));
                        String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection.second));
                        selectedStartDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        selectedEndDate = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        dateRangeBtn.setText(date1 + " - " + date2);
                        Log.d("Start", selectedStartDate.toString());
                        Log.d("end", selectedEndDate.toString());
                        Long guestUserId =  SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
                        CreateReservation createReservation = new CreateReservation(selectedStartDate, selectedEndDate, numOfguests,guestUserId, id);
                        Log.d("CRETERES", createReservation.toString());
//                        getReservationPrice
                        Call<Double> call = ClientUtils.reservationService.getReservationPrice(createReservation);
                        call.enqueue(new Callback<Double>() {
                            @Override
                            public void onResponse(Call<Double> call, Response<Double> response) {
                                if (response.isSuccessful()) {
                                    Log.d("OVO JE CENA", String.valueOf(response.body()));
                                    priceText.setText(String.valueOf(response.body()));
                                } else {
                                    Log.e("Network Request", "Failed with code: " + response.code());
                                }
                                Log.d("OVO JE CENA",String.valueOf(response.body()));
                            }

                            @Override
                            public void onFailure(Call<Double> call, Throwable t) {

                            }
                        });
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "tag");

            }
        });

        guestsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                numOfguests = Integer.valueOf(newText);
                if(selectedStartDate != null){
                    Long guestUserId =  SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
                    Call<Double> call = ClientUtils.reservationService.
                            getReservationPrice( new CreateReservation
                                    (selectedStartDate, selectedEndDate, numOfguests,guestUserId, id));
                    call.enqueue(new Callback<Double>() {
                        @Override
                        public void onResponse(Call<Double> call, Response<Double> response) {
                            if (response.isSuccessful()) {
                                Log.d("OVO JE CENA", String.valueOf(response.body()));
                                priceText.setText(String.valueOf(response.body()));
                            } else {
                                Log.e("Network Request", "Failed with code: " + response.code());
                            }
                            Log.d("OVO JE CENA",String.valueOf(response.body()));
                        }

                        @Override
                        public void onFailure(Call<Double> call, Throwable t) {

                        }
                    });
                }

            }
        });

        Button commentButton = binding.commentButton;
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("accommodationId", accommodation.getId());
                bundle.putLong("hostId", accommodation.getHost().getUser().getId());
                Log.d("ACCcom", accommodation.getId().toString());
                Log.d("ACChost", accommodation.getHost().getUser().getId().toString());
//                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                findNavController(view).navigate(R.id.action_apartmentDetailsFragment_to_commentsFragment, bundle);

            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedStartDate != null){
                    Long guestUserId =  SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
                    Call<ReservationMethod> call = ClientUtils.reservationService.
                            createReservation( new CreateReservation
                                    (selectedStartDate, selectedEndDate, numOfguests,guestUserId, id));
                    call.enqueue(new Callback<ReservationMethod>() {
                        @Override
                        public void onResponse(Call<ReservationMethod> call, Response<ReservationMethod> response) {
                            if (response.isSuccessful()) {
                                Log.d("OVO JE CENA", String.valueOf(response.body()));
                                Toast.makeText(getContext(), "Created reservation with reservation method: " +
                                        response.body().toString(), Toast.LENGTH_SHORT).show();
                                getAvaliableDates(id);
                                selectedStartDate = null;
                                dateRangeBtn.setText("DATE RANGE");
                                priceText.setText("");
                                guestsTextView.setText(numberOfguests.get(0), false);

                            } else {
                                Log.e("Network Request", "Failed with code: " + response.code());
                            }
                            Log.d("OVO JE CENA",String.valueOf(response.body()));
                        }

                        @Override
                        public void onFailure(Call<ReservationMethod> call, Throwable t) {

                        }
                    });
                }

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
                imageView.setImageBitmap(convertBase64ToBitmap(accommodation.getImages().get(0)));

                getAvaliableDates(accommodation.getId());

                prepareCardsAccList();
                prepareCardsHostList();

                numberOfguests = generateRangeList(accommodation.getMinGuest(), accommodation.getMaxGuest());
                ArrayAdapter<String> commentInputAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, numberOfguests);
                guestsTextView.setAdapter(commentInputAdapter);

                guestsTextView.setText(numberOfguests.get(0), false);

                HorizontalScrollView horizontalScrollView = binding.horizontalScrollView;
                LinearLayout linearLayout = binding.linearLayout;

                List<String> base64ImageList = accommodation.getImages();

                for (int i = 1; i < base64ImageList.size(); i++)  {
                    String base64Image = base64ImageList.get(i);
                    MaterialCardView cardView = new MaterialCardView(getContext());

                    cardView.setRadius(8);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(640, 480);
                    params.setMargins(8, 0, 8, 0);
                    cardView.setLayoutParams(params);

                    ImageView imageView = new ImageView(getContext());

                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    Bitmap bitmapImage = convertBase64ToBitmap(base64Image);

                    imageView.setImageBitmap(bitmapImage);

                    cardView.addView(imageView);

                    linearLayout.addView(cardView);
                }
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

    private ArrayList<String> generateRangeList(int start, int end) {
        ArrayList<String> result = new ArrayList<>();

        if (start <= end) {
            for (int i = start; i <= end; i++) {
                result.add(String.valueOf(i));
            }
        } else {
            for (int i = start; i >= end; i--) {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }


    public static CalendarConstraints getCalendarConstraints(List<Date> enabledDates) {
        if (enabledDates.isEmpty()) {
            long today = MaterialDatePicker.todayInUtcMilliseconds();
            CalendarConstraints.Builder emptyListConstraintsBuilder = new CalendarConstraints.Builder();
            emptyListConstraintsBuilder.setStart(today);
            emptyListConstraintsBuilder.setEnd(today);
            emptyListConstraintsBuilder.setValidator(DateValidatorPointBackward.before(today));
            return emptyListConstraintsBuilder.build();
        }

        Collections.sort(enabledDates);

        long minDate = enabledDates.get(0).getTime();
        long maxDate = enabledDates.get(enabledDates.size() - 1).getTime();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(minDate);
        constraintsBuilder.setEnd(maxDate);

        List<CalendarConstraints.DateValidator> validators = new ArrayList<>();
        validators.add(DateValidatorPointForward.from(minDate));
//        validators.add(DateValidatorPointBackward.before(maxDate));

        for (int i = 0; i < enabledDates.size() - 1; i++) {
            long startDate = enabledDates.get(i).getTime();
            long endDate = enabledDates.get(i + 1).getTime();
            long nextDay = startDate + TimeUnit.DAYS.toMillis(1);

            if (nextDay < endDate) {
                validators.add(createDisabledDateValidator(nextDay, endDate));
            }
        }

        constraintsBuilder.setValidator(CompositeDateValidator.allOf(validators));

        return constraintsBuilder.build();
    }

    // Metoda za kreiranje DateValidator-a koji onemogućava datume između dva susedna datuma
    private static CalendarConstraints.DateValidator createDisabledDateValidator(final long startDate, final long endDate) {
        return new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date < startDate || date >= endDate;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        };
    }










    public static Pair<Long, Long> getDefaultDateSelection() {
        long today = MaterialDatePicker.todayInUtcMilliseconds();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long tomorrow = calendar.getTimeInMillis();

        return new Pair<>(tomorrow, tomorrow + TimeUnit.DAYS.toMillis(1));
    }

    private void getAvaliableDates(Long id){
        Call<List<Date>> call = ClientUtils.accommodationService.getAvailableDates(id);
        call.enqueue(new Callback<List<Date>>() {
            @Override
            public void onResponse(Call<List<Date>> call, Response<List<Date>> response) {
                if(response.code() == 200){
                    Log.d("OVO je lista",response.body().toString());
                    avaliableDates = (ArrayList<Date>) response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Date>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void prepareCardsAccList(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutAcc(id);
        call.enqueue(new Callback<Collection<CommentCard>>() {
            @Override
            public void onResponse(Call<Collection<CommentCard>> call, Response<Collection<CommentCard>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Odgovor", response.body().toString());
                    Collection<CommentCard> commentCards = response.body();
                    accommodationRating = calculateAverageRating(commentCards);
                    String val = "Accommodation rating: " + (accommodationRating.equals(0.0) ? "No reviews" : accommodationRating.toString());
                    ratingAcc.setText(val);
                } else {
                    accommodationRating = 0.0;
                    Log.d("Response", "Not successful");
                }
            }

            @Override
            public void onFailure(Call<Collection<CommentCard>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void prepareCardsHostList(){

        Call<Collection<CommentCard>> call = ClientUtils.commentsService.getCommentsAboutHost(accommodation.getHost().getUser().getId());
        call.enqueue(new Callback<Collection<CommentCard>>() {
            @Override
            public void onResponse(Call<Collection<CommentCard>> call, Response<Collection<CommentCard>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Collection<CommentCard> commentCards = response.body();
                    hostRating = calculateAverageRating(commentCards);
                    String val = "Rating host: " + (hostRating.equals(0.0) ? "No reviews" : hostRating.toString());
                    ratingHost.setText(val);
                } else {
                    hostRating = 0.0;
                    Log.d("Response", "Not successful");
                }
            }

            @Override
            public void onFailure(Call<Collection<CommentCard>> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private double calculateAverageRating(Collection<CommentCard> commentCards) {
        int totalRating = 0;
        int numberOfRatings = 0;

        for (CommentCard commentCard : commentCards) {
            totalRating += commentCard.getRating();
            numberOfRatings++;
        }
        double averageRating;
        if (numberOfRatings > 0) {
            averageRating = (double) totalRating / numberOfRatings;
        } else {
            return 0.0;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return Double.parseDouble(decimalFormat.format(averageRating));
    }
}