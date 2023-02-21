package com.shyoz.fridgemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.shyoz.fridgemanager.R;

public class UserViewModel extends AndroidViewModel {

    private final MutableLiveData<String> username = new MutableLiveData<>(getApplication().getResources().getString(R.string.default_username));

    private final MutableLiveData<String> email = new MutableLiveData<>(getApplication().getResources().getString(R.string.default_email));

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username.setValue(username);
        return this;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email.setValue(email);
        return this;
    }
}