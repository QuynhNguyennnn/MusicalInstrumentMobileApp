package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.highmusicapp.Models.Bill;

import java.util.List;

@Dao
public interface BillDAO {
    @Insert
    long createBill(Bill bill);

    @Query("SELECT * FROM BILL WHERE customerID = :customerID AND status = 1")
    List<Bill> getAllBillByCustomerID(int customerID);
}
