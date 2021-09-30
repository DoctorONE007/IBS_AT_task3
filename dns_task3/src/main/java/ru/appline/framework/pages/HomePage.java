package ru.appline.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Реализация домашней страницы
 */
public class HomePage extends BasePage {
    @FindBy(xpath = "//nav//input[@type ='search']")
    private WebElement searchInput;

    @FindBy(xpath = "//nav//div[@class='ui-input-search__buttons']/span[2]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@class='cart-link__lbl']")
    private WebElement cartButton;

    /**
     * Поиск товара в строке поиска
     * @param name - имя товара
     * @return ResultsPage
     */
    public ResultsPage searchForProduct(String name) {
        waitUtilElementToBeClickable(searchInput).click();
        searchInput.clear();
        searchInput.sendKeys(name);
        searchButton.click();
        return pageManager.getResultsPage();

    }

    /**
     * Переход в корзину
     * @return CartPage
     */
    public CartPage goToCart() {
        waitUtilElementToBeClickable(cartButton).click();
        return pageManager.getCartPage();


    }


}
