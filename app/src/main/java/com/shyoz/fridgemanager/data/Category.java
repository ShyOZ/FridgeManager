package com.shyoz.fridgemanager.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Category {

    String id;

    String displayName;


    public Category() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public Category setId(String id) {
        this.id = id;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Category setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Category{" +
                ", display_name='" + displayName + '\'' +
                '}';
    }
}
