package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.CommentAboutAcc;
import com.example.bookingapplication.model.CommentAboutHost;
import com.example.bookingapplication.model.CommentCard;
import com.example.bookingapplication.model.UserReport;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutAcc/approving")
    Call<Collection<CommentCard>> getCommentsForApproving();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutHost/reported")
    Call<Collection<CommentCard>> getReportedCommentsHost();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutAcc/reported")
    Call<Collection<CommentCard>> getReportedCommentsAcc();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("commentsAboutAcc/{id}")
    Call<CommentCard> deleteCommentAcc(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("commentsAboutHost/{id}")
    Call<CommentCard> deleteCommentHost(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("commentsAboutAcc/{id}/approve/{isApproved}")
    Call<CommentCard> approveCommentAboutAcc(@Path("id") Long id, @Path("isApproved") Boolean isApproved);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutAcc/guest/{id}")
    Call<Collection<CommentCard>> getCommentsAboutAccForGuest(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("commentsAboutHost/guest/{id}")
    Call<Collection<CommentCard>> getCommentsAboutHostForGuest(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/guest/comment/{id}")
    Call<Collection<Accommodation>> getAccommodationsForComment(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("commentsAboutHost")
    Call<CommentAboutHost> createCommentAboutHost(@Body CommentAboutHost commentAboutHost);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("commentsAboutAcc")
    Call<CommentAboutAcc> createCommentAboutAcc(@Body CommentAboutAcc commentAboutAcc);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/guest/{id}")
    Call<Collection<Accommodation>> getAccommodationsForCommentHost(@Path("id") Long id);
}
