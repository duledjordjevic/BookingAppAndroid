package com.example.bookingapplication.fragments.notificationForHost;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentNotificationForHostBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.model.NotificationForHost;
import com.example.bookingapplication.model.NotificationTypeStatus;
import com.example.bookingapplication.model.enums.NotificationType;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationForHostFragment extends Fragment {

    public static ArrayList<NotificationForHost> products = new ArrayList<NotificationForHost>();
    FragmentNotificationForHostBinding binding;
    private boolean isVisibleOptions;
    private SwitchCompat reservationReqBtn;
    private SwitchCompat reservationCancelled;
    private SwitchCompat reservationCreated;
    private SwitchCompat reviewButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationForHostBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        FloatingActionButton settingsBtn = binding.settingsButton;
        reservationReqBtn = binding.notificationReservation;
        reservationCancelled = binding.notificationCancelled;
        reservationCreated = binding.notificaitonCreated;
        reviewButton = binding.notificationReview;

        getHostNotificationsStatus();

        isVisibleOptions = false;

        reservationReqBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeNotificationTypeStatus(NotificationType.RESERVATION_REQUEST,true);
                }else{
                    changeNotificationTypeStatus(NotificationType.RESERVATION_REQUEST,false);
                }
            }
        });
        reservationCancelled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeNotificationTypeStatus(NotificationType.CANCELLED_RESERVATION,true);
                }else{
                    changeNotificationTypeStatus(NotificationType.CANCELLED_RESERVATION,false);
                }
            }
        });
        reservationCreated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeNotificationTypeStatus(NotificationType.CREATED_RESERVATION,true);
                }else{
                    changeNotificationTypeStatus(NotificationType.CREATED_RESERVATION,false);
                }
            }
        });
        reviewButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeNotificationTypeStatus(NotificationType.NEW_REVIEW,true);
                }else{
                    changeNotificationTypeStatus(NotificationType.NEW_REVIEW,false);
                }
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisibleOptions){
                    Transition transition = new Slide(Gravity.TOP);

                    transition.setDuration(600);
                    transition.addTarget(R.id.scroll_notification_for_host_list);

                    TransitionManager.beginDelayedTransition(binding.ceoLayout, transition);
                    binding.optionsLayout.setVisibility(View.GONE);
                }else{
                    Transition transition = new Slide(Gravity.TOP);

                    transition.setDuration(600);
                    transition.addTarget(R.id.options_layout);
                    transition.addTarget(R.id.scroll_notification_for_host_list);

                    TransitionManager.beginDelayedTransition(binding.ceoLayout, transition);
                    binding.optionsLayout.setVisibility(View.VISIBLE);
                }
                isVisibleOptions = !isVisibleOptions;

            }
        });
        FragmentTransition.to(NotificationForHostCardListFragment.newInstance(products), getActivity() , false, R.id.scroll_notification_for_host_list);


        return root;
    }
    private void getHostNotificationsStatus(){
        Long id = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
        Call<List<NotificationTypeStatus>> call = ClientUtils.notificationService.getHostNotificationsTypeStatus(id);
        call.enqueue(new Callback<List<NotificationTypeStatus>>() {
            @Override
            public void onResponse(Call<List<NotificationTypeStatus>> call, Response<List<NotificationTypeStatus>> response) {
                Log.d("Responsee",String.valueOf(response.code()));
                if(response.code() == 200){
//                    for(NotificationTypeStatus status: response.body()){
//                        Log.d("StatusCard",status.toString());
//                    }
                    response.body().forEach(notificationTypeStatus -> {
                        if(notificationTypeStatus.getType().equals(NotificationType.RESERVATION_REQUEST)){
                            if(notificationTypeStatus.getTurned()){
                                reservationReqBtn.setChecked(true);
                            }else{
                                reservationReqBtn.setChecked(false);
                            }
                        }else if(notificationTypeStatus.getType().equals(NotificationType.CREATED_RESERVATION)){
                            if(notificationTypeStatus.getTurned()){
                                reservationCreated.setChecked(true);
                            }else{
                                reservationCreated.setChecked(false);
                            }
                        }else if(notificationTypeStatus.getType().equals(NotificationType.CANCELLED_RESERVATION)){
                            if(notificationTypeStatus.getTurned()){
                                reservationCancelled.setChecked(true);
                            }else{
                                reservationCancelled.setChecked(false);
                            }
                        }else if(notificationTypeStatus.getType().equals(NotificationType.NEW_REVIEW)){
                            if(notificationTypeStatus.getTurned()){
                                reviewButton.setChecked(true);
                            }else {
                                reviewButton.setChecked(false);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<NotificationTypeStatus>> call, Throwable t) {
                Log.d("Responsee",String.valueOf(t.getMessage()));
            }
        });

    }
    private void changeNotificationTypeStatus(NotificationType notificationType,Boolean status){
        NotificationTypeStatus notificationTypeStatus = new NotificationTypeStatus(null,null,notificationType,status,SharedPreferencesManager.
                getUserInfo(getContext().getApplicationContext()).getId());
        Call<Object> call = ClientUtils.notificationService.changeHostNotificationTypeStatus(notificationTypeStatus);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code() == 200){
                    Log.d("Resposne","succesfully updated");
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("Resposne",String.valueOf(t.getMessage()));
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}