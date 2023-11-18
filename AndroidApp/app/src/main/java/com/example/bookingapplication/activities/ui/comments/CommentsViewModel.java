package com.example.bookingapplication.activities.ui.comments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CommentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is comments fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}