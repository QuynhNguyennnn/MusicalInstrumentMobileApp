package com.example.highmusicapp.AdapterController;

public interface CartListener {
    void onRemoveProduct(int cartID, int productID);
    void onIncrease(int cartID, int productID);
    void onDecrease(int cartID, int productID);
}
