package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.highmusicapp.Models.Account;

@Dao
public interface AccountDAO {

    @Insert
    void register(Account account);

    @Query("Select *  from Account where username=:username")
    Account checkUsername(String username);

    @Query("Select * from Account where username=:username and password=:password")
    Account checkLogin(String username, String password);
}

