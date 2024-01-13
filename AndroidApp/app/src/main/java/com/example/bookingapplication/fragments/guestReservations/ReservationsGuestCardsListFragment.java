package com.example.bookingapplication.fragments.guestReservations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapplication.adapters.ReservationGuestCardListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentReservationGuestListBinding;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;
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
        prepareCardsList();
        return root;
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

    private void prepareCardsList(){
        Log.d("USAO", "USAO");
        Map<String, String> queryParams = new HashMap<>();
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
