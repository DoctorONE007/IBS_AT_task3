package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Реализация страницы с результатами поиска
 */
public class ResultsPage extends BasePage {

    @FindBy(xpath = "//div[@data-id = 'product']//a[@class= 'catalog-product__name ui-link ui-link_black']")
    private List<WebElement> listProducts;

    @FindBy(xpath = "//div[@data-id = 'product'][1]//button[2]")
    private WebElement buyButton;

    /**
     * Проверка доступен ли товар для покупки
     * @return ResultsPage
     */
    public ResultsPage checkProductAvailable() {
        if (listProducts.size() == 0)
            Assertions.fail("Товары не найдены");
        waitUtilElementToBeClickable(listProducts.get(0));
        try {
            waitUtilElementToBeClickable(buyButton);
            if (!(buyButton.getText().equals("Купить")))
                Assertions.fail("Товара нет в наличии");
        } catch (TimeoutException ex ) {
            Assertions.fail("Продажи прекращены");
        }
        return this;

    }

    /**
     * Клип по продукту
     * @return ProductPage
     */
    public ProductPage clickProduct() {
        waitUtilElementToBeClickable(listProducts.get(0)).click();
        return pageManager.getProductPage();

    }

}
