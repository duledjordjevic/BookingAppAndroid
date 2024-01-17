package com.example.bookingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.NotificationCardBinding;
import com.example.bookingapplication.model.NotificationForGuest;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationForGuestAdapter extends ArrayAdapter<NotificationForGuest> {
    private ArrayList<NotificationForGuest> aProducts;

    public NotificationForGuestAdapter(Context context, ArrayList<NotificationForGuest> products){
        super(context, R.layout.notification_card, products);
        aProducts = products;

    }

    @Override
    public int getCount() {
        return aProducts.size();
    }


    @Nullable
    @Override
    public NotificationForGuest getItem(int position) {
        return aProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private boolean isLiked = false;
    private User user;
    private NotificationCardBinding binding;
    private NotificationForGuest card;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.card = getItem(position);
        if(convertView == null){
            binding = NotificationCardBinding.inflate(LayoutInflater.from(getContext()),parent,false);

            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (NotificationCardBinding) convertView.getTag();
        }
        MaterialCardView notification_card = binding.notificationCardItem;
        ImageView imageView = binding.notificationImageView;
        TextView productTitle = binding.notificationTitleTextView;
        TextView descriptionTextView = binding.notificationDescriptionTextView;
        TextView dateTextView = binding.notificationDateTextView;

        user = SharedPreferencesManager.getUserInfo(getContext());

        if(card != null){



            descriptionTextView.setText("Description: " + card.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            dateTextView.setText(card.getDateTime().format(formatter));

            notification_card.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Clicked: "  +
                        ", id: " + getItem(position).getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
