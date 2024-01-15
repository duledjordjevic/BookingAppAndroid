package com.example.bookingapplication.fragments.guestReservations;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.ReservationGuestCardListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentReservationGuestListBinding;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsGuestCardsListFragment extends ListFragment {
    private FragmentReservationGuestListBinding binding;
    private static final String ARG_PARAM = "param";
    private ReservationGuestCardListAdapter adapter;
    private ArrayList<ReservationGuestCard> cards;

    private TextInputEditText searchInput;
    private AutoCompleteTextView statusTextView;
    private Button startDateBtn;
    private Button endDateBtn;
    private Button searchBtn;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ReservationsGuestCardsListFragment newInstance(ArrayList<ReservationGuestCard> cards){
        ReservationsGuestCardsListFragment fragment = new ReservationsGuestCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservationGuestListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchInput = binding.searchInput;
        startDateBtn = binding.startDateBtn;
        endDateBtn = binding.endDateBtn;
        searchBtn = binding.searchBtn;
        statusTextView = binding.statusTextView;
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

        String[] reservationStatusArray = getResources().getStringArray(R.array.reservation_status);
        ArrayAdapter<String> reservationStatusAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_item, reservationStatusArray);
        statusTextView.setAdapter(reservationStatusAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCards();
            }
        });

        prepareCardsList(new HashMap<>());
        return root;
    }

    private void filterCards(){
        Map<String, String> queryParams = new HashMap<>();

        if(!searchInput.getText().toString().isEmpty()){
            queryParams.put("title", searchInput.getText().toString());
        }
        if(startDate != null){
            if(endDate != null){
                if (startDate.isBefore(endDate) || startDate.isEqual(endDate)){
                    queryParams.put("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    queryParams.put("endDate", endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }else{
                    Toast.makeText(getContext(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                    return;
                }
            }else{
                Toast.makeText(getContext(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                return;
            }
        }else if(endDate != null){
            Toast.makeText(getContext(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(statusTextView.getText().toString().isEmpty() || statusTextView.getText().toString().equals("ALL"))){
            queryParams.put("status", statusTextView.getText().toString());
        }


        prepareCardsList(queryParams);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "onCreate Products List Fragment");
        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationGuestCardListAdapter(getActivity(), cards);
            setListAdapter(adapter);
            Log.i("Booking", "Adapter Products List Fragment");
        }

    }

    private void prepareCardsList(Map<String, String> queryParams){

        queryParams.put("guestId", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());
        Log.d("USAO", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());
        Call<Collection<Reservation>> call = ClientUtils.reservationService.getFilteredReservationsForGuest( queryParams);
        call.enqueue(new Callback<Collection<Reservation>>() {
            @Override
            public void onResponse(Call<Collection<Reservation>> call, Response<Collection<Reservation>> response) {
                Log.d("USAO", "USAO1");
                Log.d("Response", String.valueOf(response.code()));
//                Log.d("Response", response.body().toString());
                ArrayList<ReservationGuestCard> cards = new ArrayList<>();
                for (Reservation reservation : response.body()) {
                    ReservationGuestCard card = new ReservationGuestCard(reservation);
                    cards.add(card);
                }
                addProducts(cards);
            }

            @Override
            public void onFailure(Call<Collection<Reservation>> call, Throwable t) {
                Log.d("USAO", "USAO2");
                Log.d("Fail", t.toString());
                Log.d("Fail", "Hello");
            }
        });
    }

    private void addProducts(ArrayList<ReservationGuestCard> cards){
        this.adapter.clear();
        this.adapter.addAll(cards);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

}
