package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Accommodation;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import java.util.List;

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
}
