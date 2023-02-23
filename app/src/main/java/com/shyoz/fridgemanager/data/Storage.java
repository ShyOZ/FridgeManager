package com.shyoz.fridgemanager.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Storage {

    @Exclude
    private String id;

    private String displayName;
    private String storageOwnerId;
    private HashMap<String, Boolean> storageUserIds;

    public Storage() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public Storage setId(String id) {
        this.id = id;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Storage setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getStorageOwnerId() {
        return storageOwnerId;
    }

    public Storage setStorageOwnerId(String storageOwnerId) {
        this.storageOwnerId = storageOwnerId;
        return this;
    }

    public HashMap<String, Boolean> getStorageUserIds() {
        return storageUserIds;
    }

    public Storage setStorageUserIds(HashMap<String, Boolean> storageUserIds) {
        this.storageUserIds = storageUserIds;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Storage{" +
                ", display_name='" + displayName + '\'' +
                ", storage_owner_id='" + storageOwnerId + '\'' +
                ", storage_user_ids=" + storageUserIds +
                '}';
    }
}
