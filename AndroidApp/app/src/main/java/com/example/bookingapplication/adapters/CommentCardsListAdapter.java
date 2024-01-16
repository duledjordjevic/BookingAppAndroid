package com.example.bookingapplication.adapters;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.bookingapplication.R;
import com.example.bookingapplication.databinding.ApartmentCardBinding;
import com.example.bookingapplication.databinding.CommentCardBinding;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CommentCardsListAdapter extends ArrayAdapter<CommentCard> {
    private ArrayList<CommentCard> aProducts;
    private CommentCardBinding binding;
    private CommentCard card;
    public CommentCardsListAdapter(Context context, ArrayList<CommentCard> products){
        super(context, R.layout.comment_card, products);
        aProducts = products;

    }

    @Override
    public int getCount() {
        return aProducts.size();
    }


    @Nullable
    @Override
    public CommentCard getItem(int position) {
        return aProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.card = getItem(position);
        if(convertView == null){
            binding = CommentCardBinding.inflate(LayoutInflater.from(getContext()),parent,false);

            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (CommentCardBinding) convertView.getTag();
        }

        CardView commentCard = binding.commentCard;
        TextView content = binding.content;
        TextView title = binding.title;
        TextView date = binding.date;
        RatingBar rating = binding.ratingBar;

//        user = SharedPreferencesManager.getUserInfo(getContext());

        if(card != null){

            content.setText(card.getContent());
            title.setText(card.getAccommodation().getTitle());
            date.setText(card.getDate().toString());
            rating.setRating(card.getRating());

        }

        return convertView;
    }
}
