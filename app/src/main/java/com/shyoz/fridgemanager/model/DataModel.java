package com.shyoz.fridgemanager.model;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shyoz.fridgemanager.R;
import com.shyoz.fridgemanager.data.Category;
import com.shyoz.fridgemanager.data.DatabaseUser;
import com.shyoz.fridgemanager.data.Storage;

public class DataModel extends AndroidViewModel {

    private final MutableLiveData<String> displayName = new MutableLiveData<>(getApplication().getResources().getString(R.string.default_username));
    private final MutableLiveData<String> email = new MutableLiveData<>(getApplication().getResources().getString(R.string.default_email));
    private final MutableLiveData<Uri> photoUrl = new MutableLiveData<>();

    private final MutableLiveData<DatabaseUser> databaseUser = new MutableLiveData<>();

    private final MutableLiveData<Storage> storage = new MutableLiveData<>();

    private final MutableLiveData<Category> category = new MutableLiveData<>();

    private final MutableLiveData<String> title = new MutableLiveData<>();

    public DataModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getDisplayName() {
        return displayName;
    }

    public DataModel setDisplayName(String username) {
        this.displayName.setValue(username);
        return this;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public DataModel setEmail(String email) {
        this.email.setValue(email);
        return this;
    }

    public DataModel setPhotoUrl(Uri photoUrl) {
        this.photoUrl.setValue(photoUrl);
        return this;
    }

    public LiveData<Uri> getPhotoUrl() {
        return photoUrl;
    }

    public MutableLiveData<DatabaseUser> getDatabaseUser() {
        return databaseUser;
    }

    public DataModel setDatabaseUser(DatabaseUser databaseUser) {
        this.databaseUser.setValue(databaseUser);
        return this;
    }

    public MutableLiveData<Storage> getStorage() {
        return storage;
    }

    public DataModel setStorage(Storage storage) {
        this.storage.setValue(storage);
        return this;
    }

    public MutableLiveData<Category> getCategory() {
        return category;
    }

    public DataModel setCategory(Category category) {
        this.category.setValue(category);
        return this;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public DataModel setTitle(String title) {
        this.title.setValue(title);
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "DataModel{" +
                "displayName=" + displayName +
                ", email=" + email +
                ", databaseUser=" + databaseUser.getValue() +
                ", storage=" + storage.getValue() +
                ", category=" + category.getValue() +
                '}';
    }
}