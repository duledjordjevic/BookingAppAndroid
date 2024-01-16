package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.CommentCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CommentsService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutHost/host{hostUserid}")
    Call<List<CommentCard>> getCommentsAboutHost(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutAcc/acc/{id}")
    Call<List<CommentCard>> getCommentsAboutAcc(@Path("id") Long id);

}
