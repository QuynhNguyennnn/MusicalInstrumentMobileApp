package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Customer", foreignKeys = @ForeignKey(entity = People.class, parentColumns = "peopleID", childColumns = "peopleID"))
public class Customer extends People{
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int customerID;
    private int peopleID;
    private String customerType;
    private float accumulatedPointed;
    private boolean status;

    public Customer(String fullName, String phoneNumber, String address, String customerType, float accumulatedPointed, boolean status) {
        super(fullName, phoneNumber, address, status);
        this.customerID = customerID;
        this.peopleID = peopleID;
        this.customerType = customerType;
        this.accumulatedPointed = accumulatedPointed;
        this.status = status;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPeopleID() {
        return peopleID;
    }

    public void setPeopleID(int peopleID) {
        this.peopleID = peopleID;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public float getAccumulatedPointed() {
        return accumulatedPointed;
    }

    public void setAccumulatedPointed(float accumulatedPointed) {
        this.accumulatedPointed = accumulatedPointed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
