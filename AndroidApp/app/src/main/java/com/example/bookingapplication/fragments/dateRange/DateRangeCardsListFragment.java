package com.example.bookingapplication.fragments.dateRange;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.adapters.DateRangeCardAdapter;
import com.example.bookingapplication.adapters.ReservationGuestCardListAdapter;
import com.example.bookingapplication.databinding.FragmentDateRangeCardsListBinding;
import com.example.bookingapplication.databinding.FragmentReservationGuestListBinding;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.DateRangeCard;
import com.example.bookingapplication.model.ReservationGuestCard;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateRangeCardsListFragment extends ListFragment {

    private FragmentDateRangeCardsListBinding binding;
    private static final String ARG_PARAM = "param";
    private DateRangeCardAdapter adapter;
    private ArrayList<DateRangeCard> cards ;


    public static DateRangeCardsListFragment newInstance(ArrayList<DateRangeCard> cards){
        DateRangeCardsListFragment fragment = new DateRangeCardsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, cards);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDateRangeCardsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        prepareCards();
        for(DateRangeCard card: cards){
            Log.d("CARD", card.toString());
        }
        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cards = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new DateRangeCardAdapter(getActivity(), cards,this);
            setListAdapter(adapter);
        }

    }

    private void prepareCards(){
        ArrayList<DateRangeCard> dates = new ArrayList<>();
        dates.add(new DateRangeCard(LocalDate.now(), LocalDate.now(), 200));
        dates.add(new DateRangeCard(LocalDate.now(), LocalDate.now(), 100));
        addCards(cards);
    }
    private void addCards(ArrayList<DateRangeCard> cards){
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