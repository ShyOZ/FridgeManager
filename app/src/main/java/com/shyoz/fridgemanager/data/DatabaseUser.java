package com.shyoz.fridgemanager.data;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class DatabaseUser {
    String defaultStorageId;

    HashMap<String, Boolean> storageIds;

    public DatabaseUser() {
    }

    public String getDefaultStorageId() {
        return defaultStorageId;
    }

    public DatabaseUser setDefaultStorageId(String defaultStorageId) {
        this.defaultStorageId = defaultStorageId;
        return this;
    }

    public HashMap<String, Boolean> getStorageIds() {
        return storageIds;
    }

    public DatabaseUser setStorageIds(HashMap<String, Boolean> storageIds) {
        this.storageIds = storageIds;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "DatabaseUser{" +
                "defaultStorageId='" + defaultStorageId + '\'' +
                ", storageIds=" + storageIds +
                '}';
    }

    //    public DatabaseUser addStorageId(String storageId) {
//        if (storageIds == null) {
//            storageIds = new HashMap<>();
//        }
//        storageIds.put(storageId, true);
//        return this;
//    }
}
