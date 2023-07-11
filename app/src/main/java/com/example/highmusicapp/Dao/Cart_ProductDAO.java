package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.highmusicapp.Models.Cart_Product;

@Dao
public interface Cart_ProductDAO {
    @Insert
    void addProductToCart(Cart_Product cart_product);

    @Query("SELECT * FROM Cart_Product WHERE cartID = :cartID AND productID = :productID")
    boolean findProductInCart(int cartID, int productID);

    @Query("SELECT quantity FROM Cart_Product WHERE cartID = :cartID AND productID = :productID")
    int findQuantityProductInCart(int cartID, int productID);

    @Query("UPDATE Cart_Product SET quantity = quantity + 1 WHERE cartID = :cartID AND productID = :productID")
    void increaseQuantity(int cartID, int productID);

    @Query("UPDATE Cart_Product SET quantity = quantity - 1 WHERE cartID = :cartID AND productID = :productID")
    void decreaseQuantity(int cartID, int productID);

    @Query("DELETE FROM cart_product WHERE cartID = :cartID AND productID = :productID")
    void deleteProductToCart(int cartID, int productID);

    @Query("SELECT count(cartID) FROM Cart_Product WHERE cartID = :cartID")
    int countProductInCart(int cartID);
}
