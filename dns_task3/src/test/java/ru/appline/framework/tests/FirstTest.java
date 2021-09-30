package ru.appline.framework.tests;

import org.junit.jupiter.api.Test;
import ru.appline.framework.basetestsclass.BaseTests;


public class FirstTest extends BaseTests {
    @Test
    public void startTest() throws InterruptedException {
        int i = 0;
        app.getHomePage().searchForProduct("macbook")
                .checkProductAvailable()
                .clickProduct()
                .saveInfo(i)
                .addGuaranty(i, 2)
                .addToCart()
                .searchForProduct("samsung galaxy")
                .checkProductAvailable()
                .clickProduct()
                .saveInfo(++i)
                .addGuaranty(i, 1)
                .addToCart()
                .searchForProduct("Detroit")
                .checkProductAvailable()
                .clickProduct()
                .saveInfo(++i)
                .addToCart()
                .goToCart()
                .deleteDiscount()
                .checkSum()
                .checkGuarantyAndPrice()
                .delete(4765955)
                .checkSum()
                .add(4741289)
                .add(4741289)
                .checkSum()
                .addBack()
                .checkSum();

    }
}
