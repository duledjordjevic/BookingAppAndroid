package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.UserReport;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentsService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutHost/host/{hostUserid}")
    Call<Collection<CommentCard>> getCommentsAboutHost(@Path("hostUserid") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutAcc/acc/{id}")
    Call<Collection<CommentCard>> getCommentsAboutAcc(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("commentsAboutAcc/{id}/report/{isReported}")
    Call<CommentCard> reportCommentAboutAcc(@Path("id") Long id, @Path("isReported") Boolean isReported);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("commentsAboutHost/{id}/report/{isReported}")
    Call<CommentCard> reportCommentAboutHost(@Path("id") Long id, @Path("isReported") Boolean isReported);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("commentsAboutAcc/reportMessage/{id}")
    Call<CommentCard> setReportMessageAcc(@Path("id") Long id, @Body String message);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("commentsAboutHost/reportMessage/{id}")
    Call<CommentCard> setReportMessageHost(@Path("id") Long id, @Body String message);
}
