package org.example;

public class Product {
    private int productId;
    private String productName;
    private String manufacturer;
    private String model;
    private double purchasePrice;
    private double retailPrice;
    private int nums;
    public Product(){
        
    }

    public Product(int productId, String productName, String manufacturer, String model, double purchasePrice, double retailPrice, int nums) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
        this.nums = nums;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    @Override
    public String toString() {
        return "Product{" +
            "productId=" + productId +
            ", productName='" + productName + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", model='" + model + '\'' +
            ", purchasePrice=" + purchasePrice +
            ", retailPrice=" + retailPrice +
            ", nums=" + nums +
            '}';
    }

}
