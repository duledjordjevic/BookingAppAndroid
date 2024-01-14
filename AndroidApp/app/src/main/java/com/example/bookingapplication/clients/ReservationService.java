package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Reservation;
import com.example.bookingapplication.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ReservationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/filterGuest")
    Call<Collection<Reservation>> getFilteredReservationsForGuest(@QueryMap Map<String, String> queryParams);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("reservations/filterHost")
    Call<Collection<Reservation>> getFilteredReservationsForHost(@QueryMap Map<String, String> queryParams);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("reservations/pending/{reservationId}")
    Call<Boolean> deletePendingReservation(@Path("reservationId") Long reservationId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("reservations/cancelAccepted/{reservationId}")
    Call<Reservation> cancelAcceptedReservation(@Path("reservationId") Long reservationId);


}
