package com.example.bookingapplication.clients;

import android.util.Log;

import com.example.bookingapplication.BookingApp;
import com.example.bookingapplication.BuildConfig;
import com.example.bookingapplication.util.SharedPreferencesManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    public static final String SERVICE_API_PATH = "http://"+ BuildConfig.IP_ADDR +":8080/api/";

    public static OkHttpClient authenticatedClient(final String authToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + authToken);
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        });

        return builder.build();
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(authenticatedClient(SharedPreferencesManager.getUserInfo(BookingApp.getContext()).getJwt()))
            .build();

    public static AuthService authService = retrofit.create(AuthService.class);
    public static UpdateUserService updateUserService = retrofit.create(UpdateUserService.class);
    public static ApartmentService apartmentService = retrofit.create(ApartmentService.class);
}
