package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.example.bookingapplication.model.ApartmentCard;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AccommodationApprovingListAdapter extends ArrayAdapter<AccommodationApprovingCard> {
    private ArrayList<AccommodationApprovingCard> aProducts;
    public AccommodationApprovingListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    @Override
    public int getCount() {
        return aProducts.size();
    }
    @Nullable
    @Override
    public AccommodationApprovingCard getItem(int position) {
        return aProducts.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationApprovingCard card = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.apartment_card,
                    parent, false);
        }
        MaterialCardView apartment_card = convertView.findViewById(R.id.accommodation_approving_card_item);
        ImageView imageView = convertView.findViewById(R.id.accommodation_approving_card_image);
        TextView productTitle = convertView.findViewById(R.id.accommodation_approving_card_title);
        TextView productAddress = convertView.findViewById(R.id.accommodation_approving_card_address);
        TextView productDescription = convertView.findViewById(R.id.accommodation_approving_card_description);


        if(card != null){
            imageView.setImageResource(card.getImage());
            productTitle.setText(card.getTitle());
            productAddress.setText(card.getAddress().getState() + "," + card.getAddress().getCity() + "," + card.getAddress().getStreet());
            productDescription.setText(card.getDescription());
            apartment_card.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("Booking", "Clicked: " + card.getTitle() + ", id: " +
                        card.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + card.getTitle()  +
                        ", id: " + card.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
