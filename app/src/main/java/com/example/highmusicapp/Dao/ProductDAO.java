package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.highmusicapp.Models.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("delete from Product where productID=:productID")
    void deleteProduct(int productID);

    @Query("Select * from Product")
    List<Product> getAllProduct();

    @Query("SELECT * FROM product WHERE productID = :productID AND status = 1")
    Product getProductByID(int productID);
}
