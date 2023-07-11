package com.example.highmusicapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Database(entities = {Product.class, Category.class, Account.class}, version = 12)
public abstract class HighMusicDatabase extends RoomDatabase {
    public abstract ProductDAO getProductDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract AccountDAO getAccountDAO();

    private static final String DATABASE_NAME = "external_database.db";

    private static HighMusicDatabase INSTANCE;

    public static HighMusicDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            // Check if the external database exists
            boolean externalDatabaseExists = checkExternalDatabaseExists(context);
            if (externalDatabaseExists) {
                // Import the external database if it exists
                importExternalDatabase(context);
            }

            INSTANCE = Room.databaseBuilder(context, HighMusicDatabase.class, "HighMusicDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    private static boolean checkExternalDatabaseExists(Context context) {
        File externalDbFile = context.getDatabasePath(DATABASE_NAME);
        Log.d("Database Exists", externalDbFile.exists() + "");
        return externalDbFile.exists();
    }

    private static void importExternalDatabase(Context context) {
        File externalDbFile = context.getDatabasePath(DATABASE_NAME);

        Log.d("External DB Path", externalDbFile.toString());

        if (!externalDbFile.getParentFile().exists()) {
            externalDbFile.getParentFile().mkdirs();
        }

        try (InputStream inputStream = context.getAssets().open(DATABASE_NAME);
             OutputStream outputStream = new FileOutputStream(externalDbFile)) {

            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}