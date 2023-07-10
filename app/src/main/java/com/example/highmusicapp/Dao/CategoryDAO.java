package com.example.highmusicapp.Dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.highmusicapp.Models.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Query("delete from Category where categoryID=:categoryID")
    void deleteCategory(int categoryID);

    @Query("Select * from Category")
    List<Category> getAllCategory();
    @Query("SELECT categoryName FROM Category WHERE categoryID = :categoryID")
    String getCategoryNameById(int categoryID);
}
