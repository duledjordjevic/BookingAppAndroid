package com.example.bookingapplication.adapters;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.graphics.Color;
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
import com.example.bookingapplication.databinding.NotificationCardBinding;
import com.example.bookingapplication.fragments.notificationForHost.NotificationForHostCardListFragment;
import com.example.bookingapplication.model.NotificationForHost;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationForHostAdapter extends ArrayAdapter<NotificationForHost> {
    private ArrayList<NotificationForHost> aProducts;
    private NotificationForHostCardListFragment notificationFragment;

    public NotificationForHostAdapter(Context context, ArrayList<NotificationForHost> products, NotificationForHostCardListFragment notificationFragment){
        super(context, R.layout.notification_card, products);
        aProducts = products;
        this.notificationFragment = notificationFragment;

    }
    @Override
    public int getCount() {
        return aProducts.size();
    }

    @Nullable
    @Override
    public NotificationForHost getItem(int position) {
        return aProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private boolean isLiked = false;
    private User user;
    private NotificationCardBinding binding;
    private NotificationForHost card;
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
        Button markAsReadBtn = binding.markAsReadBtn;

        user = SharedPreferencesManager.getUserInfo(getContext());

        if(card != null){
            if(card.isRead()){
                notification_card.setCardBackgroundColor(Color.argb(224,224,224,224));
                markAsReadBtn.setVisibility(View.GONE);
            }else{
                notification_card.setCardBackgroundColor(Color.WHITE);
                markAsReadBtn.setVisibility(View.VISIBLE);
            }


            switch (card.getType()) {
                case RESERVATION_REQUEST:
                    imageView.setImageResource(R.drawable.request);
                    productTitle.setText("Reservation request");
                    break;
                case CANCELLED_RESERVATION:
                    imageView.setImageResource(R.drawable.cancel);
                    productTitle.setText("Cancelled reservation");
                    break;
                case CREATED_RESERVATION:
                    imageView.setImageResource(R.drawable.accepted);
                    productTitle.setText("Reservation created");
                    break;
                case NEW_REVIEW:
                    imageView.setImageResource(R.drawable.star);
                    productTitle.setText("New review");
                    break;
                default:
                    break;
            }

            descriptionTextView.setText("Description: " + card.getDescription());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            dateTextView.setText(card.getDateTime().format(formatter));

            markAsReadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notification_card.setCardBackgroundColor(Color.argb(224,224,224,224));
                    markAsReadBtn.setVisibility(View.GONE);
                    markAsRead(getItem(position).getId());
                }
            });

        }

        return convertView;
    }

    private void markAsRead(Long id){
        Call<NotificationForHost> call = ClientUtils.notificationService.markHostNotificationAsRead(id);
        call.enqueue(new Callback<NotificationForHost>() {
            @Override
            public void onResponse(Call<NotificationForHost> call, Response<NotificationForHost> response) {
                if(response.code() == 200){
                    notificationFragment.prepareApartmentCardsList();
                }
            }

            @Override
            public void onFailure(Call<NotificationForHost> call, Throwable t) {
                Log.d("markirano",t.getMessage());
            }
        });
    }
}