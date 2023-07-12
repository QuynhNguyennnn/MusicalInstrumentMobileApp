package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.highmusicapp.Models.People;

@Dao
public interface PeopleDAO {
    @Insert
    long insertPeople(People people);
}
