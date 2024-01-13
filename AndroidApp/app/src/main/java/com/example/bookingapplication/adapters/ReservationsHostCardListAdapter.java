package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ReservationGuestCardBinding;
import com.example.bookingapplication.databinding.ReservationHostCardBinding;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.model.ReservationHostCard;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReservationsHostCardListAdapter extends ArrayAdapter<ReservationHostCard> {

    private ArrayList<ReservationHostCard> reservations;

    public ReservationsHostCardListAdapter(@NonNull Context context, ArrayList<ReservationHostCard> resource) {
        super(context, R.layout.reservation_host_card, resource);
        reservations = resource;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Nullable
    @Override
    public ReservationHostCard getItem(int position) {return reservations.get(position);}

    public void deleteItem(int position){
        reservations.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationHostCardBinding binding;
        ReservationHostCard card = getItem(position);
        Log.d("Booking", "error1");
        if (convertView == null) {
            binding = ReservationHostCardBinding.inflate(LayoutInflater.from(getContext()), parent, false);

            convertView = binding.getRoot();

            convertView.setTag(binding);
        }
        else {
            binding = (ReservationHostCardBinding) convertView.getTag();
        }

        TextView titleTextView = binding.titleTextView;
        TextView guestTextView = binding.guestTextView;
        TextView guestCancellationsTextView = binding.guestCancellationsTextView;
        TextView startDateTextView = binding.startDateTextView;
        TextView endDateTextView = binding.endDateTextView;
        TextView statusTextView = binding.statusTextView;
        TextView priceTextView = binding.priceTextView;
        Button acceptBtn = binding.acceptBtn;
        Button declineBtn = binding.declineBtn;

        if(card != null){
            titleTextView.setText(card.getAccommodation().getTitle());
            guestTextView.setText(card.getGuest().getName() + " " + card.getGuest().getLastName());
            guestCancellationsTextView.setText(String.valueOf(card.getGuest().getNumberOfCancellation()));
            startDateTextView.setText(card.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            endDateTextView.setText(card.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            statusTextView.setText(card.getStatus().toString());
            priceTextView.setText(Double.toString(card.getPrice()));

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        return convertView;
    }
}
