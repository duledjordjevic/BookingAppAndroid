package com.example.bookingapplication.activities.ui.hostProperties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HostPropertiesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HostPropertiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is host Properties fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}