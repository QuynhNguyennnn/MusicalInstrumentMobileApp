package com.example.highmusicapp.AdapterController;

import com.example.highmusicapp.Models.Product;

public interface ProductListener {
    void onUpdateProduct(Product product);
    void onDeleteProduct(int id, int pos);
}
