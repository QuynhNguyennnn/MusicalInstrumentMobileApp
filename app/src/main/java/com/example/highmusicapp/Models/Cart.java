package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Cart", foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "customerID", childColumns = "customerID"))
public class Cart {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int cartID;
    private int customerID;
    private boolean status;

    public Cart(int customerID, boolean status) {
        this.customerID = customerID;
        this.status = status;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
