package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.AccApprovalStatus;
import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.AccommodationCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.DateRangeCard;
import com.example.bookingapplication.model.enums.AccommodationApprovalStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<Accommodation> createAccommodation(@Body Accommodation accommodation);

    @Headers({
            "User-Agent: Mobile-Android"
    })
    @Multipart
    @POST("images/{accommodation_id}")
    Call<Void> createAccommodationImages(@Path("accommodation_id") Long accommodation_id,   @Part List<MultipartBody.Part> image);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations/priceList/{accommodation_id}")
    Call<Void> addPriceList(@Path("accommodation_id") Long accommodation_id, @Body ArrayList<DateRangeCard> priceLists);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}")
    Call<Accommodation> getAccommodationDetails(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/adminApproving")
    Call<List<Card>> getAccommodationsForApproving();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/host/{host_id}")
    Call<List<Card>> getAccommodationsForHosts(@Path("host_id") Long host_id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/{id}/approvalStatus")
    Call<Accommodation> changeAccApprovalStatus(@Path("id") Long id,@Body AccApprovalStatus approvalStatus);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/{id}")
    Call<Accommodation> updateAccommodation(@Body Accommodation accommodation,@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/intervalPrices/{id}")
    Call<List<DateRangeCard>> getDateIntervals(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}/availableDates")
    Call<List<Date>> getAvailableDates(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/cards/filter")
    Call<List<Card>> filterAccommodations(@QueryMap Map<String, String> queryParams);
}
