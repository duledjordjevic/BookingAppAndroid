package com.example.bookingapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ReservationHostCardBinding;
import com.example.bookingapplication.dialog.UserReportDialog;
import com.example.bookingapplication.fragments.hostReservations.ReservationsHostCardsListFragment;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationHostCard;
import com.example.bookingapplication.model.enums.ReservationStatus;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsHostCardListAdapter extends ArrayAdapter<ReservationHostCard> {

    private ReservationsHostCardsListFragment fragment;
    private ArrayList<ReservationHostCard> reservations;
    private ReservationsHostCardsListFragment reservationsFragment;

    public ReservationsHostCardListAdapter(@NonNull Context context, ReservationsHostCardsListFragment fragment, ArrayList<ReservationHostCard> resource,
                                           ReservationsHostCardsListFragment reservationsFragment) {
        super(context, R.layout.reservation_host_card, resource);
        this.fragment = fragment;
        reservations = resource;
        this.reservationsFragment = reservationsFragment;
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
        Button reportBtn = binding.reportButton;

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
            if(card.getEndDate().isBefore(LocalDate.now()) && card.getStatus().equals(ReservationStatus.ACCEPTED) && !card.isGuestReported()){
                reportBtn.setEnabled(true);
                reportBtn.setAlpha(1f);
                reportBtn.setTextColor(getContext().getColor(R.color.white));
            }else{
                reportBtn.setEnabled(false);
                reportBtn.setAlpha(0.5f);
                reportBtn.setTextColor(getContext().getColor(R.color.white));
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

            reportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog reportDialog =new UserReportDialog(getContext(),R.style.CustomDialog,card.getGuest().getUser().getId(),
                            SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId(),card.getId(),reservationsFragment);
                    reportDialog.setCancelable(true);
                    reportDialog.setCanceledOnTouchOutside(true);
                    WindowManager.LayoutParams lp=reportDialog.getWindow().getAttributes();
                    lp.dimAmount=0.6f;  // dimAmount between 0.0f and 1.0f, 1.0f is completely dark
                    reportDialog.getWindow().setAttributes(lp);
                    reportDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    reportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    reportDialog.show();
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
