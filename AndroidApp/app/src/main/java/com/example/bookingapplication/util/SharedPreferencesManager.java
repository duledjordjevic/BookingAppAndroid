package com.example.bookingapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.enums.UserType;

public class SharedPreferencesManager {

    private static final String PREFERENCE_NAME = "MyAppPreferences";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_ID = "user_id";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveUserInfo(Context context, String email, UserType userType, int id) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ROLE, userType.toString());
        editor.putInt(KEY_ID, id);
        editor.apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        String email = preferences.getString(KEY_EMAIL, "");
        String userRole = preferences.getString(KEY_USER_ROLE, UserType.ADMIN.toString());
        int id = preferences.getInt(KEY_ID, 0);
        return new User((long) id, email, "",  UserType.valueOf(userRole), "");
    }

    public static void clearUserInfo(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_USER_ROLE);
        editor.remove(KEY_ID);
        editor.apply();
    }
}
