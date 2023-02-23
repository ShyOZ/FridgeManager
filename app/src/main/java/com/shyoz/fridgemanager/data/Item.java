package com.shyoz.fridgemanager.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Item {

    private String id;
    private String displayName;
    private String added;
    private String expiration;
    private Integer quantity;
    private String unit;
    private String notes;

    public Item() {
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Item setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    private String stringAsDate(String input) {
        String date;
        try {
            LocalDate.parse(input);
            date = input;
        } catch (DateTimeParseException e) {
            Log.e("Item", "String not a LocalDate format: ", e);
            date = "";
        }

        return date;
    }

    public String getAdded() {
        return added;
    }

    public Item setAdded(String added) {
        this.added = stringAsDate(added);
        return this;
    }

    public String getExpiration() {
        return expiration;
    }

    public Item setExpiration(String expiration) {
        this.expiration = stringAsDate(expiration);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Item setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Item setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item{" +
                ", name='" + displayName + '\'' +
                ", dateAdded=" + added +
                ", dateExpires=" + expiration +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
