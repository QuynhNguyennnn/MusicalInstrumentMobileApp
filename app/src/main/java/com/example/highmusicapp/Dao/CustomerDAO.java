package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.highmusicapp.Models.Customer;

@Dao
public interface CustomerDAO {
    @Insert
    void insertCustomer(Customer customer);
}
