package com.example.bookingapplication.fragments.hostReservations;

import android.app.Activity;
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

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.ReservationGuestCardListAdapter;
import com.example.bookingapplication.adapters.ReservationsHostCardListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentReservationGuestListBinding;
import com.example.bookingapplication.databinding.FragmentReservationHostListBinding;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.helpers.DatePickerDialogHelper;
import com.example.bookingapplication.model.DateRange;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.model.ReservationHostCard;
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

public class ReservationsHostCardsListFragment extends ListFragment {
    private FragmentReservationHostListBinding binding;
    private static final String ARG_PARAM = "param";
    private ReservationsHostCardListAdapter adapter;
    private ArrayList<ReservationHostCard> cards;

    private TextInputEditText searchInput;
    private AutoCompleteTextView statusTextView;
    private Button startDateBtn;
    private Button endDateBtn;
    private Button searchBtn;
    private DateRange dateRange;
    private DatePickerDialogHelper datePickerDialogHelper;


    public static ReservationsHostCardsListFragment newInstance(ArrayList<ReservationHostCard> cards){
        ReservationsHostCardsListFragment fragment = new ReservationsHostCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservationHostListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.dateRange = new DateRange();
        
        searchInput = binding.searchInput;
        startDateBtn = binding.startDateBtn;
        endDateBtn = binding.endDateBtn;
        searchBtn = binding.searchBtn;
        statusTextView = binding.statusTextView;

        datePickerDialogHelper = new DatePickerDialogHelper(startDateBtn, endDateBtn, dateRange);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogHelper.showDatePickerDialog(getContext(), "startDate");
            }
        });
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogHelper.showDatePickerDialog(getContext(), "endDate");
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
        if(dateRange.getStartDate() != null){
            if(dateRange.getEndDate() != null){
                if (dateRange.getStartDate().isBefore(dateRange.getEndDate()) || dateRange.getStartDate().isEqual(dateRange.getEndDate())){
                    queryParams.put("startDate", dateRange.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    queryParams.put("endDate", dateRange.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }else{
                    Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                    return;
                }
            }else{
                Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
                return;
            }
        }else if(dateRange.getEndDate() != null){
            Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
            return;
        }else{
            Toast.makeText(getActivity(), "Date wrong. Start date must be before end date", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(statusTextView.getText().toString().isEmpty() || statusTextView.getText().toString().equals("ALL"))){
            queryParams.put("status", statusTextView.getText().toString());
        }

//        queryParams.put("hostId", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());

        prepareCardsList(queryParams);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "onCreate Products List Fragment");
        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReservationsHostCardListAdapter(getActivity(), this, cards);
            setListAdapter(adapter);
            Log.i("Booking", "Adapter Products List Fragment");
        }

    }

    public void prepareCardsList(Map<String, String> queryParams){
        Log.d("PARAMS", queryParams.toString());
        queryParams.put("hostId", SharedPreferencesManager.getUserInfo(getContext()).getId().toString());
        Call<Collection<Reservation>> call = ClientUtils.reservationService.getFilteredReservationsForHost( queryParams);
        call.enqueue(new Callback<Collection<Reservation>>() {
            @Override
            public void onResponse(Call<Collection<Reservation>> call, Response<Collection<Reservation>> response) {
                Log.d("USAO", "USAO1");
                Log.d("Response", String.valueOf(response.code()));
//                Log.d("Response", response.body().toString());
                if(response.code() == 200){
                    ArrayList<ReservationHostCard> cards = new ArrayList<>();
                    for (Reservation reservation : response.body()) {
                        ReservationHostCard card = new ReservationHostCard(reservation);
                        cards.add(card);
                    }
                    addProducts(cards);
                }
            }

            @Override
            public void onFailure(Call<Collection<Reservation>> call, Throwable t) {
                Log.d("USAO", "USAO2");
                Log.d("Fail", t.toString());
                Log.d("Fail", "Hello");
            }
        });
    }

    private void addProducts(ArrayList<ReservationHostCard> cards){
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
