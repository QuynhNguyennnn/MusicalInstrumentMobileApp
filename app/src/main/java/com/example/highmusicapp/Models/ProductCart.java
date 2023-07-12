package com.example.highmusicapp.Models;

public class ProductCart {
    private int cartID;
    private int customerID;
    private int ID;
    private int productID;
    private int quantity;
    private double price;
    private int categoryID;
    private String productName;
    private String material;
    private int yearOfManufacture;
    private String description;
    private String image;

    public ProductCart(int cartID, int customerID, int ID, int productID, int quantity, double price, int categoryID, String productName, String material, int yearOfManufacture, String description, String image) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.ID = ID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
        this.productName = productName;
        this.material = material;
        this.yearOfManufacture = yearOfManufacture;
        this.description = description;
        this.image = image;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
