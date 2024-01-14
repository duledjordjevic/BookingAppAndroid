package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ReservationHostCardBinding;
import com.example.bookingapplication.fragments.hostReservations.ReservationsHostCardsListFragment;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationHostCard;
import com.example.bookingapplication.model.enums.ReservationStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsHostCardListAdapter extends ArrayAdapter<ReservationHostCard> {

    private ReservationsHostCardsListFragment fragment;
    private ArrayList<ReservationHostCard> reservations;

    public ReservationsHostCardListAdapter(@NonNull Context context, ReservationsHostCardsListFragment fragment, ArrayList<ReservationHostCard> resource) {
        super(context, R.layout.reservation_host_card, resource);
        this.fragment = fragment;
        reservations = resource;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Nullable
    @Override
    public ReservationHostCard getItem(int position) {return reservations.get(position);}

    public void changeStatus(int position, ReservationStatus reservationStatus){
        reservations.get(position).setStatus(reservationStatus);
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

            if (card.getStatus().equals(ReservationStatus.PENDING)){
                acceptBtn.setEnabled(true);
                acceptBtn.getBackground().setTint(getContext().getColor(R.color.dark_blue));
                acceptBtn.setTextColor(getContext().getColor(R.color.white));
                acceptBtn.setAlpha(1f);
                declineBtn.setEnabled(true);
                declineBtn.getBackground().setTint(getContext().getColor(R.color.dark_blue));
                declineBtn.setTextColor(getContext().getColor(R.color.white));
                declineBtn.setAlpha(1f);
            }else{
                acceptBtn.setEnabled(false);
                acceptBtn.getBackground().setTint(getContext().getColor(R.color.dark_blue));
                acceptBtn.setTextColor(getContext().getColor(R.color.white));
                acceptBtn.setAlpha(0.5f);
                declineBtn.setEnabled(false);
                declineBtn.getBackground().setTint(getContext().getColor(R.color.dark_blue));
                declineBtn.setTextColor(getContext().getColor(R.color.white));
                declineBtn.setAlpha(0.5f);
            }

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateReservationStatus(card.getId(), ReservationStatus.ACCEPTED, getPosition(card));
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateReservationStatus(card.getId(), ReservationStatus.DECLINED, getPosition(card));
                }
            });
        }


        return convertView;
    }

    void updateReservationStatus(Long reservationId,ReservationStatus reservationStatus, int position){
        Call<Reservation> call = ClientUtils.reservationService.updateReservationStatus(reservationId, reservationStatus);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                Log.d("USAO", "USAO1");
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 201){
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_LONG).show();
                    fragment.prepareCardsList(new HashMap<>());
                }else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Log.d("USAO", "USAO2");
                Log.d("Fail", t.toString());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
