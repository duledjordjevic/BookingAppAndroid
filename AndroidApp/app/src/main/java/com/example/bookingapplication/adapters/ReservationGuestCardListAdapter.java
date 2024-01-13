package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ReservationGuestCardBinding;
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.google.android.material.card.MaterialCardView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReservationGuestCardListAdapter  extends ArrayAdapter<ReservationGuestCard> {
    private ArrayList<ReservationGuestCard> reservations;
    public ReservationGuestCardListAdapter(@NonNull Context context, ArrayList<ReservationGuestCard> resource) {
        super(context, R.layout.reservation_guest_card, resource);
        reservations = resource;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Nullable
    @Override
    public ReservationGuestCard getItem(int position) {return reservations.get(position);}

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
        ReservationGuestCardBinding binding;
        ReservationGuestCard card = getItem(position);
        Log.d("Booking", "error1");
        if (convertView == null) {
            binding = ReservationGuestCardBinding.inflate(LayoutInflater.from(getContext()), parent, false);

            convertView = binding.getRoot();

            convertView.setTag(binding);
        }
        else {
            binding = (ReservationGuestCardBinding) convertView.getTag();
        }

        TextView titleTextView = binding.titleTextView;
        TextView cancellationPolicyTextView = binding.cancellationPolicyTextView;
        TextView startDateTextView = binding.startDateTextView;
        TextView endDateTextView = binding.endDateTextView;
        TextView statusTextView = binding.statusTextView;
        TextView priceTextView = binding.priceTextView;
        Button deleteBtn = binding.deleteBtn;
        Button cancelBtn = binding.cancelBtn;

        if(card != null){
            titleTextView.setText(card.getAccommodation().getTitle());
            cancellationPolicyTextView.setText(card.getAccommodation().getCancellationPolicy().toString());
            startDateTextView.setText(card.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            endDateTextView.setText(card.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            statusTextView.setText(card.getStatus().toString());
            priceTextView.setText(Double.toString(card.getPrice()));

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        return convertView;
    }
}
