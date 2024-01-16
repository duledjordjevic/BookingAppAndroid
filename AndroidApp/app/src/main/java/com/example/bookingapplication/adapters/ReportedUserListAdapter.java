package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.UserBlockCardBinding;
import com.example.bookingapplication.fragments.reportedUsers.ReportedUsersCardsListFragment;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.UserBlockCard;
import com.google.android.material.card.MaterialCardView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedUserListAdapter extends ArrayAdapter<UserBlockCard> {
    private ArrayList<UserBlockCard> aProducts ;
    private ReportedUsersCardsListFragment reportedUsersFragment;
    public ReportedUserListAdapter(@NonNull Context context, ArrayList<UserBlockCard> resource,
                                   ReportedUsersCardsListFragment reportedUsersFragment) {
        super(context, R.layout.user_block_card, resource);
        this.reportedUsersFragment = reportedUsersFragment;
        aProducts = resource;

    }
    @Override
    public int getCount() {
        return aProducts.size();
    }
    @Nullable
    @Override
    public UserBlockCard getItem(int position) {
        return aProducts.get(position);
    }
    public void deleteItem(int position){
        aProducts.remove(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserBlockCard card = getItem(position);
        UserBlockCardBinding binding;
        if(convertView == null){
            binding = UserBlockCardBinding.inflate(LayoutInflater.from(getContext()),parent,false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (UserBlockCardBinding) convertView.getTag();
        }

        MaterialCardView user_block_card = binding.userBlockCardItem;
        TextView accTitleTextView = binding.titleTextView;
        TextView reportedUserTextView = binding.reportedUserTextView;
        TextView reportingUserTextView = binding.reportingUserTextView;
        TextView reasonTextView = binding.reasonTextView;
        TextView startDateTextView = binding.startDateTextView;
        TextView endDateTextView = binding.endDateTextView;
        Button blockUserBtn = binding.blockBtn;



        if(card != null){

            accTitleTextView.setText(card.getAccommodationTitle());
            reportedUserTextView.setText(" " + card.getReportedUser().getName() + " " + card.getReportingUser().getLastname() + " " +
                    String.valueOf(card.getReportedUser().getUserRole()));
            reportingUserTextView.setText(" "+ card.getReportingUser().getName() + " " + card.getReportingUser().getLastname() + " " +
                    String.valueOf(card.getReportingUser().getUserRole()));
            reasonTextView.setText(" " + card.getReason());
            startDateTextView.setText(" " + card.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            endDateTextView.setText(card.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            blockUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Block user",Toast.LENGTH_SHORT).show();
                    Call<User> call = ClientUtils.userService.blockUser(card.getReportedUser().getId());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.code() == 200){
                                Log.d("BlockUser", "Blocked user");
                                reportedUsersFragment.prepareApartmentCardsList();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });
        }

        return convertView;
    }
}
