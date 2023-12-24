package com.example.bookingapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.model.ApartmentCard;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ApartmentCardsListAdapter extends ArrayAdapter<ApartmentCard> {
    private ArrayList<ApartmentCard> aProducts;

    public ApartmentCardsListAdapter(Context context, ArrayList<ApartmentCard> products){
        super(context, R.layout.apartment_card, products);
        aProducts = products;

    }

    @Override
    public int getCount() {
        return aProducts.size();
    }


    @Nullable
    @Override
    public ApartmentCard getItem(int position) {
        return aProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ApartmentCard card = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.apartment_card,
                    parent, false);
        }
        MaterialCardView apartment_card = convertView.findViewById(R.id.apartment_card_item);
        ImageView imageView = convertView.findViewById(R.id.apartment_card_image);
        TextView productTitle = convertView.findViewById(R.id.apartment_card_title);
        TextView product_desc11 = convertView.findViewById(R.id.apartment_card_desc11);
        TextView product_desc12 = convertView.findViewById(R.id.apartment_card_desc12);

        if(card != null){
            imageView.setImageResource(card.getImage());
            productTitle.setText(card.getTitle());
            product_desc11.setText(card.getDescriptionInfo());
            product_desc12.setText(card.getDescriptionRating());
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
