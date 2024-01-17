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

import com.example.bookingapplication.adapters.CommentCardsListAdapter;
import com.example.bookingapplication.clients.ClientUtils;
import com.example.bookingapplication.databinding.ReportCommentBinding;
import com.example.bookingapplication.databinding.ReportPopupBinding;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.UserReport;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentReportDialog extends Dialog {

    private ReportCommentBinding binding;
    private Long accommodationId;
    private Long commentId;
    private CommentCardsListAdapter commentCardsListAdapter;
    public CommentReportDialog(@NonNull Context context, int themeResId, Long accommodationId,
                               Long commentId, CommentCardsListAdapter commentCardsListAdapter) {
        super(context, themeResId);
        this.accommodationId = accommodationId;
        this.commentId = commentId;
        this.commentCardsListAdapter = commentCardsListAdapter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ReportCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setContentView(binding.getRoot(),new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        binding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = binding.commentReportInput.getText().toString();
                Toast.makeText(getContext(),"Report comment",Toast.LENGTH_SHORT).show();
                if(reason.length() < 10) {
                    binding.reasonValidator.setText("You must enter at least 10 characters");
                    return;
                }

                if (accommodationId == null) reportCommentHost(reason);
                else reportCommentAcc(reason);
            }
        });

    }

    private void reportCommentAcc(String reason){
        Call<CommentCard> call = ClientUtils.commentsService.reportCommentAboutAcc(this.commentId, true);
        call.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 201){
                    Log.d("CommentReportAcc","CommentReportAccCreated");
                    setReportCommentAcc(reason);
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }

    private void setReportCommentAcc(String reason){
        Call<CommentCard> callNext = ClientUtils.commentsService.setReportMessageAcc(this.commentId, reason);
        callNext.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 201){
                    Log.d("SetCommentReportAcc","SetCommentReportHostCreated");
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }
    private void reportCommentHost(String reason){
        Call<CommentCard> call = ClientUtils.commentsService.reportCommentAboutHost(this.commentId, true);
        call.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 201){
                    Log.d("CommentReportHost","CommentReportAccCreated");
                    setReportCommentHost(reason);
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });

    }

    private void setReportCommentHost(String reason){
        Call<CommentCard> callNext = ClientUtils.commentsService.setReportMessageHost(this.commentId, reason);
        callNext.enqueue(new Callback<CommentCard>() {
            @Override
            public void onResponse(Call<CommentCard> call, Response<CommentCard> response) {
                if(response.code() == 201){
                    Log.d("SetCommentReportHost","SetCommentReportHostCreated");
                    cancel();
                }
            }

            @Override
            public void onFailure(Call<CommentCard> call, Throwable t) {
                Log.d("Fail", t.toString());
            }
        });
    }
}
