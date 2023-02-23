package com.shyoz.fridgemanager.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DataManager {

    private static DataManager instance;
    private final DatabaseReference categoriesReference;
    private final DatabaseReference itemsReference;
    private final DatabaseReference storagesReference;
    private final DatabaseReference usersReference;

    private DataManager() {
        DatabaseReference baseReference = FirebaseDatabase.getInstance().getReference();
        categoriesReference = baseReference.child("categories");
        itemsReference = baseReference.child("items");
        storagesReference = baseReference.child("storages");
        usersReference = baseReference.child("users");
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void loadUserData(@NonNull String userId, ValueEventListener listener) {
        usersReference.child(userId).addListenerForSingleValueEvent(listener);
    }

    public void loadStorage(@NonNull String storageId, ValueEventListener listener) {
        storagesReference.child(storageId).addListenerForSingleValueEvent(listener);
    }

    public Query getCategories(@NonNull String storageId) {
        return categoriesReference.child(storageId).orderByKey();
    }

    public Query getCategoriesByDisplayName(@NonNull String storageId) {
        return categoriesReference.child(storageId).orderByChild("displayName");
    }

    public Query getItems(@NonNull String categoryId) {
        return itemsReference.child(categoryId).orderByKey();
    }

    public Query getItemsByDisplayName(@NonNull String categoryId) {
        return itemsReference.child(categoryId).orderByChild("displayName");
    }

    public Query getItemsByExpiration(@NonNull String categoryId) {
        return itemsReference.child(categoryId).orderByChild("expiration");
    }

    public Query getItemsByAdded(@NonNull String categoryId) {
        return itemsReference.child(categoryId).orderByChild("added");
    }
}
