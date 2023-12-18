package com.example.bookingapplication.fragments.accountAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountAdminViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AccountAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}