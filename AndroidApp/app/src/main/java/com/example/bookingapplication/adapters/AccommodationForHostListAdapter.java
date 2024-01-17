package com.example.bookingapplication.adapters;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ApartmentCardBinding;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AccommodationForHostListAdapter  extends ArrayAdapter<ApartmentCard> {
    private ArrayList<ApartmentCard> aProducts;

    public AccommodationForHostListAdapter(Context context, ArrayList<ApartmentCard> products){
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
    private boolean isLiked = false;
    private User user;
    private ApartmentCardBinding binding;
    private ApartmentCard card;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.card = getItem(position);
        if(convertView == null){
            binding = ApartmentCardBinding.inflate(LayoutInflater.from(getContext()),parent,false);

            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (ApartmentCardBinding) convertView.getTag();
        }
        MaterialCardView apartment_card = binding.apartmentCardItem;
        ImageView imageView = binding.apartmentCardImage;
        TextView productTitle = binding.apartmentCardTitle;
        TextView product_desc11 = binding.apartmentCardDesc11;
        TextView product_desc12 = binding.apartmentCardDesc12;
        ImageView likeBtnImage = binding.heartButton;
        Animation zoomInAnim = AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in);
        Animation zoomOutAnim = AnimationUtils.loadAnimation(getContext(),R.anim.zoom_out);
        user = SharedPreferencesManager.getUserInfo(getContext());

        if(card != null){
            Log.d("Kartica",String.valueOf(card.getIsLiked()));
            if(card.getIsLiked() != null){
                if(card.getIsLiked()){
                    likeBtnImage.setImageResource(R.drawable.full_heart);
                }else{
                    likeBtnImage.setImageResource(R.drawable.ic_heart);
                }
            }

            imageView.setImageBitmap(convertBase64ToBitmap(card.getImage()));

            productTitle.setText(card.getTitle());
            product_desc11.setText(card.getDescriptionInfo());
            product_desc12.setText(card.getDescriptionRating());
            apartment_card.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Clicked: " + getItem(position).getTitle()  +
                        ", id: " + getItem(position).getId().toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putLong("apartmentId", getItem(position).getId());
                findNavController(v).navigate(R.id.updateAccommodationFragment,bundle);
            });
        }

        return convertView;
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
