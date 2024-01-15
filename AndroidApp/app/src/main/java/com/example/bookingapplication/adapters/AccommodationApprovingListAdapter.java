package com.example.bookingapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.model.AccommodationApprovingCard;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AccommodationApprovingListAdapter extends ArrayAdapter<AccommodationApprovingCard> {
    private ArrayList<AccommodationApprovingCard> aProducts ;
    public AccommodationApprovingListAdapter(@NonNull Context context, ArrayList<AccommodationApprovingCard> resource) {
        super(context, R.layout.accommodation_approving_card, resource);
        aProducts = resource;

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
        AccommodationApprovingCard card = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_approving_card,
                    parent, false);
        }
        MaterialCardView apartment_card = convertView.findViewById(R.id.accommodation_approving_card_item);
        ImageView imageView = convertView.findViewById(R.id.accommodation_approving_card_image);
        TextView productTitle = convertView.findViewById(R.id.accommodation_approving_card_title);
        TextView productAddress = convertView.findViewById(R.id.accommodation_approving_card_address);
        TextView productDescription = convertView.findViewById(R.id.accommodation_approving_card_description);
        Button approveBtn = convertView.findViewById(R.id.accommodation_approving_card_approve);
        Button declineBtn = convertView.findViewById(R.id.accommodation_approving_card_decline);

        if(card != null){
            imageView.setImageBitmap(convertBase64ToBitmap(card.getImage()));
            productTitle.setText(card.getTitle());
            productAddress.setText(card.getAddress().getState() + "," + card.getAddress().getCity() + "," + card.getAddress().getStreet());
            productDescription.setText(card.getDescription());
            approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Approved: " + card.getTitle()  +
                            ", id: " + card.getId().toString(), Toast.LENGTH_SHORT).show();
                    deleteItem(getPosition(card));
                    notifyDataSetChanged();
                }
            });
            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Decline: " + card.getTitle()  +
                            ", id: " + card.getId().toString(), Toast.LENGTH_SHORT).show();
                    deleteItem(getPosition(card));
                    notifyDataSetChanged();
                }
            });


        }

        return convertView;
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64, 0);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
