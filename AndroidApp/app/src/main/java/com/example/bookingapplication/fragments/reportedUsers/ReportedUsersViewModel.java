package com.example.bookingapplication.fragments.reportedUsers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportedUsersViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ReportedUsersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reported Users fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}