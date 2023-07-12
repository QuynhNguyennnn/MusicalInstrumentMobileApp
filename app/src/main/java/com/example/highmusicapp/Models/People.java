package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
@Entity(tableName = "People")
public class People implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int peopleID;
    private String fullName;
    private String phoneNumber;
    private String address;
    private boolean status;

    public People(String fullName, String phoneNumber, String address, boolean status) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    public int getPeopleID() {
        return peopleID;
    }

    public void setPeopleID(int peopleID) {
        this.peopleID = peopleID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
