package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Bill", foreignKeys = {@ForeignKey(entity = Customer.class, parentColumns = "customerID", childColumns = "customerID")})
public class Bill {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int billID;
    private int customerID;
    private String purchaseDate;
    private float total;
    private boolean status;

    public Bill(int customerID, String purchaseDate, float total, boolean status) {
        this.customerID = customerID;
        this.purchaseDate = purchaseDate;
        this.total = total;
        this.status = status;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
