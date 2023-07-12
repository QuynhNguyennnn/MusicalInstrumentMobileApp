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
            "FROM cart_product " +
            "INNER JOIN product ON cart_product.productID = product.productID " +
            "INNER JOIN cart ON cart_product.cartID = cart.cartID " +
            "WHERE customerID = :customerID AND cart_product.status = 1")
    List<Product> getProductsFromCart(int customerID);

    @Insert
    long createCart(Cart cart);

    @Query("SELECT cartID FROM Cart WHERE customerID = :customerID AND status = 1")
    long getCartIDByCustomerID(int customerID);

    @Query("SELECT * FROM Cart WHERE customerID = :customerID AND status = 1")
    boolean findCartByCustomerID(int customerID);
}
