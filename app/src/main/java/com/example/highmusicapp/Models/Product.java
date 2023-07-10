package com.example.highmusicapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "Product", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "categoryID", childColumns = "categoryID"))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int productID;
    private double price;
    private int categoryID;
    private String productName;
    private String material;
    private int quantity;
    private int yearOfManufacture;
    private String description;
    private String image;
    private boolean status;

    public Product(double price, int categoryID, String productName, String material, int quantity, int yearOfManufacture, String description, String image, boolean status) {
        this.price = price;
        this.categoryID = categoryID;
        this.productName = productName;
        this.material = material;
        this.quantity = quantity;
        this.yearOfManufacture = yearOfManufacture;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", price=" + price +
                ", categoryID=" + categoryID +
                ", productName='" + productName + '\'' +
                ", material='" + material + '\'' +
                ", quantity=" + quantity +
                ", yearOfManufacture=" + yearOfManufacture +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                '}';
    }
}
