package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Cart_Product",
        foreignKeys = {
                @ForeignKey(entity = Cart.class, parentColumns = "cartID", childColumns = "cartID"),
                @ForeignKey(entity = Product.class, parentColumns = "productID", childColumns = "productID")
        })
public class Cart_Product {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;
    private int cartID;
    private int productID;
    private int quantity;
    private boolean status;

    public Cart_Product(int cartID, int productID, int quantity, boolean status) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
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
