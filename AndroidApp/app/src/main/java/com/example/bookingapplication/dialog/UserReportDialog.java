package com.example.bookingapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ReportPopupBinding;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.fragments.hostReservations.ReservationsHostCardsListFragment;
import com.example.bookingapplication.model.UserReport;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReportDialog extends Dialog {

    private ReportPopupBinding binding;
    private Long userReportedId;
    private Long userReportingId;
    private Long reservationId;
    private ReservationsGuestCardsListFragment reservationsGuestFragment;
    private ReservationsHostCardsListFragment reservationsHostFragment;

    public UserReportDialog(@NonNull Context context, int themeResId, Long userReportedId, Long userReportingId, Long reservationId,
                            ReservationsGuestCardsListFragment reservationsFragment) {
        super(context, themeResId);
        this.userReportedId = userReportedId;
        this.userReportingId = userReportingId;
        this.reservationId = reservationId;
        this.reservationsGuestFragment = reservationsFragment;
    }
    public UserReportDialog(@NonNull Context context, int themeResId, Long userReportedId, Long userReportingId, Long reservationId,
                            ReservationsHostCardsListFragment reservationsFragment) {
        super(context, themeResId);
        this.userReportedId = userReportedId;
        this.userReportingId = userReportingId;
        this.reservationId = reservationId;
        this.reservationsHostFragment = reservationsFragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ReportPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getWindow().setContentView(binding.getRoot(),new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        binding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = binding.userReportInput.getText().toString();
                Toast.makeText(getContext(),"Report user",Toast.LENGTH_SHORT).show();
                if(reason.length() < 10) {
                    binding.reasonValidator.setText("You must enter at least 10 characters");
                    return;
                }
                UserReport userReport = new UserReport(null,userReportedId,userReportingId,reason,reservationId);
                Call<UserReport> call = ClientUtils.userService.createUserReport(userReport);
                call.enqueue(new Callback<UserReport>() {
                    @Override
                    public void onResponse(Call<UserReport> call, Response<UserReport> response) {
                        if(response.code() == 201){
                            Log.d("UserReport","UserReportCreated");
                            if(reservationsGuestFragment != null){
                                reservationsGuestFragment.prepareCardsList(new HashMap<>());
                            }else{
                                reservationsHostFragment.prepareCardsList(new HashMap<>());
                            }
                            cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserReport> call, Throwable t) {

                    }
                });

            }
        });

    }
}
