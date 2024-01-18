package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.DateRangeCardBinding;
import com.example.bookingapplication.databinding.ReservationGuestCardBinding;
import com.example.bookingapplication.fragments.createAccommodation.CreateAccommodationFragment;
import com.example.bookingapplication.fragments.dateRange.DateRangeCardsListFragment;
import com.example.bookingapplication.model.DateRangeCard;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.model.enums.ReservationStatus;

import java.util.ArrayList;

public class DateRangeCardAdapter extends ArrayAdapter<DateRangeCard> {

    private ArrayList<DateRangeCard> dateRangeCards;
    private DateRangeCardsListFragment dateRangeCardsListFragment;
    public DateRangeCardAdapter(Context context, ArrayList<DateRangeCard> resource,
                                DateRangeCardsListFragment dateRangeCardsListFragment) {
        super(context, R.layout.date_range_card, resource);
        this.dateRangeCards = resource;
        this.dateRangeCardsListFragment = dateRangeCardsListFragment;
    }


    @Override
    public int getCount() {
        return dateRangeCards.size();
    }

    @Nullable
    @Override
    public DateRangeCard getItem(int position) {return dateRangeCards.get(position);}

    public void deleteItem(int position){
        dateRangeCards.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DateRangeCardBinding binding;
        DateRangeCard card = getItem(position);
        if (convertView == null) {
            binding = DateRangeCardBinding.inflate(LayoutInflater.from(getContext()), parent, false);

            convertView = binding.getRoot();

            convertView.setTag(binding);
        }
        else {
            binding = (DateRangeCardBinding) convertView.getTag();
        }


        TextView dateTextView = binding.dateTextView;
        ImageButton deleteElementBtn = binding.deleteElementBtn;
        TextView priceTextView = binding.priceTextView;
        if(card != null){
            dateTextView.setText(card.getStartDate().toString() + " - " + card.getEndDate().toString());
            priceTextView.setText(String.valueOf("$ " + card.getPrice()));
            deleteElementBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getPosition(card));
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

}
