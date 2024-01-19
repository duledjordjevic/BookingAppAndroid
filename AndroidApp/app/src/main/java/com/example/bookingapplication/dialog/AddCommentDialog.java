package com.example.bookingapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookingapplication.adapters.AddCommentsCardsListAdapter;
import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.AddCommentPopupBinding;
import com.example.bookingapplication.databinding.ReportCommentBinding;
import com.example.bookingapplication.model.CommentAboutAcc;
import com.example.bookingapplication.model.CommentAboutHost;
import com.example.bookingapplication.model.CommentCard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentDialog extends Dialog {

    private AddCommentPopupBinding binding;
    private Long accommodationId;
    private Long hostId;
    private Long guestId;
    private AddCommentsCardsListAdapter addCommentsCardsListAdapter;
    public AddCommentDialog(@NonNull Context context, int themeResId, Long accommodationId,
                               Long hostId, Long guestId, AddCommentsCardsListAdapter addCommentsCardsListAdapter) {
        super(context, themeResId);
        this.accommodationId = accommodationId;
        this.hostId = hostId;
        this.guestId = guestId;
        this.addCommentsCardsListAdapter = addCommentsCardsListAdapter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = AddCommentPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setContentView(binding.getRoot(),new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        binding.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = binding.commentReportInput.getText().toString();
                Integer rating = Math.round(binding.ratingBar.getRating());
                Toast.makeText(getContext(),"Add comment",Toast.LENGTH_SHORT).show();
                if(reason.length() < 1) {
                    binding.reasonValidator.setText("You must enter at least 1 character");
                    return;
                }

                if (addCommentsCardsListAdapter.isCommentForAcc == true) createCommentAcc(reason, rating);
                else createCommentHost(reason, rating);
            }
        });

    }

    private void createCommentAcc(String content, int rating){
        CommentAboutAcc commentAboutAcc = new CommentAboutAcc(rating, content, this.guestId, this.accommodationId);
        Call<CommentAboutAcc> call = ClientUtils.commentsService.createCommentAboutAcc(commentAboutAcc);
        call.enqueue(new Callback<CommentAboutAcc>() {
            @Override
            public void onResponse(Call<CommentAboutAcc> call, Response<CommentAboutAcc> response) {
                if(response.code() == 201){
                    Log.d("OVDEEEE","CommentReportAccCreated");
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentAboutAcc> call, Throwable t) {
                Log.d("FailASSAD", t.toString());
            }
        });
    }

    private void createCommentHost(String content, int rating){
        CommentAboutHost commentAboutHost = new CommentAboutHost(rating, content, this.guestId, this.hostId);
        Call<CommentAboutHost> call = ClientUtils.commentsService.createCommentAboutHost(commentAboutHost);
        call.enqueue(new Callback<CommentAboutHost>() {
            @Override
            public void onResponse(Call<CommentAboutHost> call, Response<CommentAboutHost> response) {
                if(response.code() == 201){
                    Log.d("OVDEEEE","CommentReportAccCreated");
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentAboutHost> call, Throwable t) {
                Log.d("FailASSAD", t.toString());
            }
        });
    }
}
