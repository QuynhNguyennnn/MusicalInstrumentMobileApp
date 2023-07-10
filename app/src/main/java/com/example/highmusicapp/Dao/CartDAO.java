package com.example.highmusicapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.highmusicapp.Models.Cart;
import com.example.highmusicapp.Models.Product;
import com.example.highmusicapp.Models.ProductCart;

import java.util.List;

@Dao
public interface CartDAO {
    @Query("SELECT Product.productID, Product.price, Product.categoryID, Product.productName, Product.material, Cart_Product.quantity, Product.yearOfManufacture, Product.description, Product.image, Product.status " +
            "FROM Cart_Product " +
            "INNER JOIN Product ON Cart_Product.productID = Product.productID " +
            "INNER JOIN Cart ON Cart_Product.cartID = Cart.cartID " +
            "WHERE customerID = :customerID AND Cart.status = 1")
    List<Product> getProductsFromCart(int customerID);

    @Insert
    long createCart(Cart cart);

    @Query("SELECT cartID FROM Cart WHERE customerID = :customerID")
    long getCartIDByCustomerID(int customerID);

    @Query("SELECT * FROM Cart WHERE customerID = :customerID")
    boolean findCartByCustomerID(int customerID);
}
