package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Bill_Product",
        foreignKeys = {
            @ForeignKey(entity = Bill.class, parentColumns = "billID", childColumns = "billID"),
            @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID")
        })
public class Bill_Product {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int ID;
    private int billID;
    private int productID;
    private int quantity;
    private boolean status;

    public Bill_Product(int billID, int productID, int quantity, boolean status) {
        this.billID = billID;
        this.productID = productID;
        this.quantity = quantity;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
