package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Реализация домашней страницы
 */
public class HomePage extends BasePage {
    @FindBy(xpath = "//nav//input[@type ='search']")
    private WebElement searchInput;

    @FindBy(xpath = "//nav//div[@class='ui-input-search__buttons']/span[contains(@class,'icon_search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@class='cart-link__lbl']")
    private WebElement cartButton;

    /**
     * Поиск товара в строке поиска
     * @param name - имя товара
     * @return ResultsPage
     */
    public ProductPage searchForProduct(String name) {
        Assertions.assertTrue(waitUtilElementToBeClickable(searchInput), "Поисковая строка не кликабельна");
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(name);
        Assertions.assertEquals(searchInput.getAttribute("value"),name,"Введенный текст не совпадает");
        searchButton.click();
        pageManager.getResultsPage().checkProductAvailable(name);
        pageManager.getProductPage().saveInfo(name);
        return pageManager.getProductPage();
    }

    /**
     * Переход в корзину
     * @return CartPage
     */
    public CartPage goToCart() throws InterruptedException {
        Assertions.assertTrue(waitUtilElementToBeClickable(cartButton), "Кнопка корзины не кликабельна");
        cartButton.click();
        pageManager.getCartPage()
                .deleteDiscount()
                .checkSum()
                .checkGuarantyAndPrice();

        return pageManager.getCartPage();


    }


}
