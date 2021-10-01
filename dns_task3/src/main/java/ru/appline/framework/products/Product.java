package ru.appline.framework.products;

/**
 * Класс продуктов
 */
public class Product {
    private String searchedName;
    private String name;
    private double price;
    private final boolean guaranty;
    private String description;
    private int yearsGuaranty;
    private int quantity = 1;
    private final int id;
    private int indexInArray;

    public String getSearchedName(){return searchedName;}
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIndexInArray() { return indexInArray; }


    public boolean getGuaranty() {
        return guaranty;
    }
    public Product(String searchedName, String name, double price, boolean guaranty, String description, int id,int indexInArray) {
        this.searchedName = searchedName;
        this.name = name;
        this.price = price;
        this.guaranty = guaranty;
        this.description = description;
        this.id = id;
        this.indexInArray=indexInArray;
    }


}
