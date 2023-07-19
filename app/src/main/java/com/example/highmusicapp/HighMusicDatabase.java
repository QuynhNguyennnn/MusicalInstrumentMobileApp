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
import com.example.highmusicapp.Dao.BillDAO;
import com.example.highmusicapp.Dao.Bill_ProductDAO;
import com.example.highmusicapp.Dao.CartDAO;
import com.example.highmusicapp.Dao.Cart_ProductDAO;
import com.example.highmusicapp.Dao.CategoryDAO;
import com.example.highmusicapp.Dao.CustomerDAO;
import com.example.highmusicapp.Dao.PeopleDAO;
import com.example.highmusicapp.Dao.ProductDAO;
import com.example.highmusicapp.Models.Account;
import com.example.highmusicapp.Models.Bill;
import com.example.highmusicapp.Models.Bill_Product;
import com.example.highmusicapp.Models.Cart;
import com.example.highmusicapp.Models.Cart_Product;
import com.example.highmusicapp.Models.Category;
import com.example.highmusicapp.Models.Customer;
import com.example.highmusicapp.Models.People;
import com.example.highmusicapp.Models.Product;

@Database(entities = {Product.class, Category.class, Account.class, People.class, Customer.class, Cart.class, Cart_Product.class, Bill.class, Bill_Product.class}, version = 35)
public abstract  class HighMusicDatabase extends RoomDatabase  {
    public abstract ProductDAO getProductDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract AccountDAO getAccountDAO();
    public abstract CustomerDAO getCustomerDAO();
    public abstract PeopleDAO getPeopleDAO();
    public abstract CartDAO getCartDAO();
    public abstract Cart_ProductDAO getCart_ProductDAO();
    public abstract BillDAO getBillDAO();
    public abstract Bill_ProductDAO getBillProductDAO();

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
