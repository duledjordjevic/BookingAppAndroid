package com.example.bookingapplication.fragments.notificationForGuest;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.FragmentNotificationsBinding;
import com.example.bookingapplication.fragments.FragmentTransition;
import com.example.bookingapplication.fragments.notificationForHost.NotificationForHostCardListFragment;
import com.example.bookingapplication.model.NotificationForGuest;
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

public class NotificationForGuestFragment extends Fragment {

    public static ArrayList<NotificationForGuest> products = new ArrayList<NotificationForGuest>();
    private FragmentNotificationsBinding binding;
    private boolean isVisibleOptions;
    private SwitchCompat reservationReqBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FloatingActionButton settingsBtn = binding.settingsButton;
        reservationReqBtn = binding.notificationReservation;

        getGuestNotificationsStatus();

        reservationReqBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeNotificationTypeStatus(NotificationType.RESERVATION_REQUEST_RESPOND,true);
                }else{
                    changeNotificationTypeStatus(NotificationType.RESERVATION_REQUEST_RESPOND,false);
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

        FragmentTransition.to(NotificationForGuestCardListFragment.newInstance(products), getActivity() , false, R.id.scroll_notification_for_guest_list);



        return root;
    }

    private void getGuestNotificationsStatus(){
        Long id = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
        Call<List<NotificationTypeStatus>> call = ClientUtils.notificationService.getGuestNotificationsTypeStatus(id);
        call.enqueue(new Callback<List<NotificationTypeStatus>>() {
            @Override
            public void onResponse(Call<List<NotificationTypeStatus>> call, Response<List<NotificationTypeStatus>> response) {
                Log.d("Responsee",String.valueOf(response.code()));
                if(response.code() == 200){
                    Log.d("StatusCard",String.valueOf(response.body().size()));

                    for(NotificationTypeStatus status: response.body()){
                        Log.d("StatusCard",status.toString());
                    }
                    if(response.body().get(0).getTurned()){
                        reservationReqBtn.setChecked(true);
                    }else {
                        reservationReqBtn.setChecked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NotificationTypeStatus>> call, Throwable t) {
                Log.d("Responsee",String.valueOf(t.getMessage()));
            }
        });

    }

    private void changeNotificationTypeStatus(NotificationType notificationType,Boolean status){
        NotificationTypeStatus notificationTypeStatus = new NotificationTypeStatus(null,null,notificationType,status, SharedPreferencesManager.
                getUserInfo(getContext().getApplicationContext()).getId());
        Call<Object> call = ClientUtils.notificationService.changeGuestNotificationTypeStatus(notificationTypeStatus);
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