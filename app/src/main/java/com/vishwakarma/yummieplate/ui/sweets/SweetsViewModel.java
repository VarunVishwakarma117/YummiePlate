package com.vishwakarma.yummieplate.ui.sweets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SweetsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SweetsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}