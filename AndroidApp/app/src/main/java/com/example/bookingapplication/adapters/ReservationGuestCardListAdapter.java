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

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ReservationGuestCardBinding;
import com.example.bookingapplication.dialog.UserReportDialog;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.ReservationGuestCard;
import com.example.bookingapplication.model.ReservationHostCard;
import com.example.bookingapplication.model.enums.CancellationPolicy;
import com.example.bookingapplication.model.enums.ReservationStatus;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationGuestCardListAdapter  extends ArrayAdapter<ReservationGuestCard> {
    private ArrayList<ReservationGuestCard> reservations;
    private ReservationsGuestCardsListFragment reservationFragment;
    public ReservationGuestCardListAdapter(@NonNull Context context, ArrayList<ReservationGuestCard> resource,
                                           ReservationsGuestCardsListFragment reservationFragment) {
        super(context, R.layout.reservation_guest_card, resource);
        this.reservations = resource;
        this.reservationFragment = reservationFragment;
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


    public void changeStatus(int position) {
        reservations.get(position).setStatus(ReservationStatus.CANCELLED);
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
        TextView hostTextView = binding.hostTextView;
        Button deleteBtn = binding.deleteBtn;
        Button cancelBtn = binding.cancelBtn;
        Button reportBtn = binding.reportButton;

        if(card != null){
            titleTextView.setText(card.getAccommodation().getTitle());
            cancellationPolicyTextView.setText(card.getAccommodation().getCancellationPolicy().toString());
            startDateTextView.setText(card.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            endDateTextView.setText(card.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            statusTextView.setText(card.getStatus().toString());
            priceTextView.setText(Double.toString(card.getPrice()));
            hostTextView.setText(" " + card.getAccommodation().getHost().getName() +" " +  card.getAccommodation().getHost().getLastName());

            if(!card.getStatus().equals(ReservationStatus.PENDING)){
                deleteBtn.setEnabled(false);
                deleteBtn.getBackground().setTint(getContext().getColor(R.color.red_light));
                deleteBtn.setTextColor(getContext().getColor(R.color.white));
                deleteBtn.setAlpha(0.5f);
            }else{
                deleteBtn.setEnabled(true);
                deleteBtn.getBackground().setTint(getContext().getColor(R.color.red));
                deleteBtn.setTextColor(getContext().getColor(R.color.white));
                deleteBtn.setAlpha(1f);
            }

            if(!(card.getStatus().equals(ReservationStatus.ACCEPTED)) || card.getAccommodation().getCancellationPolicy() == CancellationPolicy.NON_REFUNDABLE ){
                cancelBtn.setEnabled(false);
                cancelBtn.getBackground().setTint(getContext().getColor(R.color.red_light));
                cancelBtn.setTextColor(getContext().getColor(R.color.white));
                cancelBtn.setAlpha(0.5f);
            }else{
                cancelBtn.setEnabled(true);
                cancelBtn.getBackground().setTint(getContext().getColor(R.color.red));
                cancelBtn.setTextColor(getContext().getColor(R.color.white));
                cancelBtn.setAlpha(1f);
            }
            Log.d("ReservationId",String.valueOf(card.getId()));
            Log.d("IsHostReported",String.valueOf(card.isHostReported()));
            if(card.getEndDate().isBefore(LocalDate.now()) && card.getStatus().equals(ReservationStatus.ACCEPTED) && !card.isHostReported()){
                reportBtn.setEnabled(true);
                reportBtn.setAlpha(1f);
                reportBtn.setTextColor(getContext().getColor(R.color.white));
            }else{
                reportBtn.setEnabled(false);
                reportBtn.setAlpha(0.5f);
                reportBtn.setTextColor(getContext().getColor(R.color.white));
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletePendingReservation(card.getId(), getPosition(card));
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelAcceptedReservation(card.getId(), getPosition(card));
                }
            });

            reportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog reportDialog =new UserReportDialog(getContext(),R.style.CustomDialog,card.getAccommodation().getHost().getUser().getId(),
                            SharedPreferencesManager.getUserInfo(getContext()).getId(),card.getId(),reservationFragment);
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

    void deletePendingReservation(Long reservationId, int position){
        Call<Boolean> call = ClientUtils.reservationService.deletePendingReservation(reservationId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("USAO", "USAO1");
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    Toast.makeText(getContext(), "You successful deleted this reservation", Toast.LENGTH_LONG).show();
                    deleteItem(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("USAO", "USAO2");
                Log.d("Fail", t.toString());
                Log.d("Fail", "Hello");
            }
        });
    }

    void cancelAcceptedReservation(Long reservationId, int position){
        Call<Reservation> call = ClientUtils.reservationService.cancelAcceptedReservation(reservationId);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                Log.d("USAO", "USAO1");
                Log.d("Response", String.valueOf(response.code()));

                if(response.code() == 200){
                    Toast.makeText(getContext(), "You successful cancelled this reservation", Toast.LENGTH_LONG).show();
                    changeStatus(position);
                    notifyDataSetChanged();
                }else if(response.code() == 405){
                    Toast.makeText(getContext(), "You can't cancel this reservation", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Log.d("USAO", "USAO2");
                Log.d("Fail", t.toString());
                Log.d("Fail", "Hello");
            }
        });
    }


}
