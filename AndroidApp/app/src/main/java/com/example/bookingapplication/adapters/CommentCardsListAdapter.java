package com.example.bookingapplication.adapters;

import static android.app.PendingIntent.getActivity;
import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
import static androidx.navigation.Navigation.findNavController;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.bookingapplication.dialog.CommentReportDialog;
import com.example.bookingapplication.dialog.UserReportDialog;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.fragments.guestReservations.ReservationsGuestCardsListFragment;
import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.SharedPreferencesManager;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CommentCardsListAdapter extends ArrayAdapter<CommentCard> {
    private ArrayList<CommentCard> comments;
    private CommentCardBinding binding;
    private CommentCard card;
    public CommentCardsListAdapter(Context context, ArrayList<CommentCard> products){
        super(context, R.layout.comment_card, products);
        comments = products;
    }

    @Override
    public int getCount() {
        return comments.size();
    }


    @Nullable
    @Override
    public CommentCard getItem(int position) {
        return comments.get(position);
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

        TextView content = binding.content;
        TextView title = binding.title;
        TextView date = binding.date;
        RatingBar rating = binding.ratingBar;
        TextView reportReasonLabel = binding.reportReasonLabel;
        TextView reportReason = binding.reportReasonText;

        reportReasonLabel.setVisibility(View.GONE);
        reportReason.setVisibility(View.GONE);

        Button approveButton = binding.approveButton;
        approveButton.setVisibility(View.GONE);

        Button deleteButton = binding.deleteButton;
        deleteButton.setVisibility(View.GONE);

        Button reportButton = binding.reportButton;

        UserType role = SharedPreferencesManager.getUserInfo(getContext()).getUserRole();
        switch (role) {
            case GUEST:
            case ADMIN:
                reportButton.setVisibility(View.GONE);
                break;
            case HOST:
                reportButton.setVisibility(View.VISIBLE);
                reportButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentCard cardComm = getItem(position);
                        Log.d("OVO je ID", cardComm.getId().toString());
                        Dialog reportDialog = new CommentReportDialog(getContext(),R.style.CustomDialog, cardComm.getAccommodationId(),
                                cardComm.getId(), CommentCardsListAdapter.this);
                        reportDialog.setCancelable(true);
                        reportDialog.setCanceledOnTouchOutside(true);
                        WindowManager.LayoutParams lp=reportDialog.getWindow().getAttributes();
                        lp.dimAmount=0.6f;  // dimAmount between 0.0f and 1.0f, 1.0f is completely dark
                        reportDialog.getWindow().setAttributes(lp);
                        reportDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        reportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        reportDialog.show();
                    }
                });
                break;
            default:
                break;
        }

        if(card != null){
            content.setText(card.getContent());
            date.setText(card.getDate().toString());
            rating.setRating(card.getRating());
            title.setText(card.getAccommodationTitle() == null ? card.getHostNameSurname() :
                    card.getAccommodationTitle());
        }

        return convertView;
    }
}
