package com.example.bookingapplication.adapters;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ApartmentCardBinding;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class FavouritesAccListAdapter extends ArrayAdapter<ApartmentCard> {
    private ArrayList<ApartmentCard> aProducts;

    public FavouritesAccListAdapter(Context context, ArrayList<ApartmentCard> products){
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
        ApartmentCardBinding binding;
        ApartmentCard card = getItem(position);
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

        if(card != null){

            imageView.setImageBitmap(convertBase64ToBitmap(card.getImage()));

            productTitle.setText(card.getTitle());
            product_desc11.setText(card.getDescriptionInfo());
            product_desc12.setText(card.getDescriptionRating());
            likeBtnImage.setImageResource(R.drawable.full_heart);
            apartment_card.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Clicked: " + card.getTitle()  +
                        ", id: " + card.getId().toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putLong("apartmentId", card.getId());
            });
            likeBtnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeBtnImage.setImageResource(R.drawable.ic_heart);
                    likeBtnImage.startAnimation(zoomInAnim);
                    removeFavouriteAcc(getItem(position).getId());
                    deleteItem(getPosition(card));
                    notifyDataSetChanged();
                }
            });

        }

        return convertView;
    }
    private void removeFavouriteAcc(Long accId){
        Long userId = SharedPreferencesManager.getUserInfo(getContext().getApplicationContext()).getId();
        Call<Boolean> call = ClientUtils.guestService.removeFavourite(userId,accId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("AccId",String.valueOf(accId));
                Log.d("ResponseKkod",String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
