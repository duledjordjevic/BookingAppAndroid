package com.example.bookingapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapplication.R;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.CommentCardBinding;
import com.example.bookingapplication.dialog.CommentReportDialog;
import com.example.bookingapplication.fragments.approveComments.ApproveCommentsCardListFragment;
import com.example.bookingapplication.fragments.comments.CommentCardListFragment;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.enums.UserType;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveCommentCardsListAdapter extends ArrayAdapter<CommentCard> {
    private ArrayList<CommentCard> comments;
    private CommentCardBinding binding;
    private CommentCard card;
    private ApproveCommentsCardListFragment approveCommentsCardListFragment;
    public ApproveCommentCardsListAdapter(Context context, ArrayList<CommentCard> products, ApproveCommentsCardListFragment approveCommentsCardListFragment){
        super(context, R.layout.comment_card, products);
        comments = products;
        this.approveCommentsCardListFragment = approveCommentsCardListFragment;
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

        Button approveButton = binding.approveButton;
        Button deleteButton = binding.deleteButton;

        approveButton.setVisibility(card.getReportMessage().equals("") ? View.VISIBLE : View.GONE);
        deleteButton.setVisibility(card.getReportMessage().equals("") ? View.GONE : View.VISIBLE);
        reportReasonLabel.setVisibility(card.getReportMessage().equals("") ? View.GONE : View.VISIBLE);
        reportReason.setVisibility(card.getReportMessage().equals("") ? View.GONE : View.VISIBLE);

        Button reportButton = binding.reportButton;
        reportButton.setVisibility(View.GONE);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentCard cardForApprove = getItem(position);
                approveComment(cardForApprove.getId());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentCard cardForDelete = getItem(position);
                if (cardForDelete.getAccommodationId() == null){
                    deleteCommentHost(cardForDelete.getId());
                }
                else {
                    deleteCommentAcc(cardForDelete.getId());
                }

            }
        });

        if(card != null){
            content.setText(card.getContent());
            date.setText(card.getDate().toString());
            rating.setRating(card.getRating());
            title.setText(card.getAccommodationTitle() == null ? card.getHostNameSurname() :
                    card.getAccommodationTitle());
            reportReason.setText(card.getReportMessage());
        }

        return convertView;
    }

    private void approveComment(Long commentId){
        Call<CommentCard> callNext = ClientUtils.commentsService.approveCommentAboutAcc(commentId, true);
        callNext.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 201){
                    approveCommentsCardListFragment.prepareCommentsApproving();
                    Log.d("SetCommentReportAcc","SetCommentReportHostCreated");
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void deleteCommentAcc(Long commentId){
        Call<CommentCard> callNext = ClientUtils.commentsService.deleteCommentAcc(commentId);
        callNext.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 204){
                    approveCommentsCardListFragment.prepareReportedCommentsAcc();
                    Log.d("SetCommentReportAcc","SetCommentReportHostCreated");
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void deleteCommentHost(Long commentId){
        Call<CommentCard> callNext = ClientUtils.commentsService.deleteCommentHost(commentId);
        callNext.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 204){
                    approveCommentsCardListFragment.prepareReportedCommentsHost();
                    Log.d("SetCommentReportAcc","SetCommentReportHostCreated");
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }
}
