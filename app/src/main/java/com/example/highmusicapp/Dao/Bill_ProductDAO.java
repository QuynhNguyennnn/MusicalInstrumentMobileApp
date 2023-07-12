package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.highmusicapp.Models.Bill_Product;

import java.util.List;

@Dao
public interface Bill_ProductDAO {
    @Insert
    void insertBill(Bill_Product billProduct);

    @Query("SELECT * FROM bill_product WHERE billID = :billID AND status = 1")
    List<Bill_Product> getAllProductFromBill(int billID);
}
