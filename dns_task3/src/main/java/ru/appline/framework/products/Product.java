package ru.appline.framework.products;

/**
 * Класс продуктов
 */
public class Product {
    private String name;
    private double price;
    private final boolean guaranty;
    private String description;
    private int yearsGuaranty;
    private int quantity;
    private final int id;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYearsGuaranty() {
        return yearsGuaranty;
    }

    public void setYearsGuaranty(int yearsGuaranty) {
        this.yearsGuaranty = yearsGuaranty;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getGuaranty() {
        return guaranty;
    }
    public Product(String name, double price, boolean guaranty, String description, int id) {
        this.name = name;
        this.price = price;
        this.guaranty = guaranty;
        this.description = description;
        quantity = 1;
        this.id = id;
    }


}
