package com.example.bookingapplication.activities.ui.addedProperties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddedPropertiesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddedPropertiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is added Properties fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}