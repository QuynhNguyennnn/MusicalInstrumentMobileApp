package com.example.highmusicapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.highmusicapp.Dao.AccountDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.Models.Category;
import com.example.highmusicapp.Models.Product;

@Database(entities = {Product.class, Category.class, Account.class}, version = 9)
public abstract  class HighMusicDatabase extends RoomDatabase  {
    public abstract ProductDAO getProductDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract AccountDAO getAccountDAO();

    public static HighMusicDatabase INSTANCE;

    public static HighMusicDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, HighMusicDatabase.class, "HighMusicDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
