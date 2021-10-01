package ru.appline.framework.tests;

import org.junit.jupiter.api.Test;
import ru.appline.framework.basetestsclass.BaseTests;


public class FirstTest extends BaseTests {
    @Test
    public void startTest() throws InterruptedException {
        int i = 0;
        app.getHomePage()
                .searchForProduct("MacBook")
                .addGuaranty(2)
                .addToCart()
                .searchForProduct("Samsung Galaxy")
                .addGuaranty(1)
                .addToCart()
                .searchForProduct("Detroit")
                .addToCart()
                .goToCart()
                .delete("Samsung Galaxy")
                .add("MacBook")
                .add("MacBook")
                .addBack();

    }
}
