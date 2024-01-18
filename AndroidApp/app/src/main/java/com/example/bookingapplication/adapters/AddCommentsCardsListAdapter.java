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
import com.example.bookingapplication.databinding.AbleToCommentCardBinding;
import com.example.bookingapplication.databinding.CommentCardBinding;
import com.example.bookingapplication.dialog.AddCommentDialog;
import com.example.bookingapplication.dialog.CommentReportDialog;
import com.example.bookingapplication.fragments.addComments.AddCommentsCardListFragment;
import com.example.bookingapplication.fragments.myComments.MyCommentsCardListFragment;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.CommentCard;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentsCardsListAdapter extends ArrayAdapter<Accommodation> {
    private ArrayList<Accommodation> comments;
    private AbleToCommentCardBinding binding;
    private Accommodation card;
    private AddCommentsCardListFragment addCommentsCardListFragment;
    public Boolean isCommentForAcc;

    public AddCommentsCardsListAdapter(Context context, ArrayList<Accommodation> products,
                                       AddCommentsCardListFragment addCommentsCardListFragment){
        super(context, R.layout.able_to_comment_card, products);
        this.addCommentsCardListFragment = addCommentsCardListFragment;
        comments = products;
        this.isCommentForAcc = true;
    }

    public Boolean getCommentForAcc() {
        return isCommentForAcc;
    }

    public void setCommentForAcc(Boolean commentForAcc) {
        isCommentForAcc = commentForAcc;
    }

    @Override
    public int getCount() {
        return comments.size();
    }


    @Nullable
    @Override
    public Accommodation getItem(int position) {
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
            binding = AbleToCommentCardBinding.inflate(LayoutInflater.from(getContext()),parent,false);

            convertView = binding.getRoot();
            convertView.setTag(binding);
        }else{
            binding = (AbleToCommentCardBinding) convertView.getTag();
        }

        TextView title = binding.title;
        TextView hotelOrAddress = binding.hotelOrAddress;

        Button commentButton = binding.comment;

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accommodation cardForAdd = getItem(position);
                Log.d("OVO je ID", cardForAdd.getId().toString());
                Dialog reportDialog = new AddCommentDialog(getContext(),R.style.CustomDialog, cardForAdd.getId(),
                        cardForAdd.getHost().getId(), addCommentsCardListFragment.guestUserId, AddCommentsCardsListAdapter.this);
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

        if(card != null){
            if(this.isCommentForAcc){
                title.setText(card.getTitle());
                String address = card.getAddress().getCity() + ", " + card.getAddress().getStreet();
                hotelOrAddress.setText(address);
            }
            else {
                String text = card.getHost().getName() + " " + card.getHost().getLastName();
                title.setText(text);
                hotelOrAddress.setText(card.getTitle());
            }
        }

        return convertView;
    }

}
