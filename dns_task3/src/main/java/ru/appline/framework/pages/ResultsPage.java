package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Реализация страницы с результатами поиска
 */
public class ResultsPage extends BasePage {

    @FindBy(xpath = "//div[@data-id = 'product']")
    private List<WebElement> listProducts;


    /**
     * Проверка доступен ли товар для покупки
     *
     * @return ResultsPage
     */
    public ProductPage checkProductAvailable(String name) {
        for (WebElement element : listProducts) {
            Assertions.assertTrue(waitUtilElementToBeVisible(element),"Элемент не загрузился");
            if (element.findElement(By.xpath(".//span")).getText().contains(name)) {
                try {
                    element.findElement(By.xpath(".//button[contains(@class,'buy')]"));
                    element.findElement(By.xpath(".//a[@class= 'catalog-product__name ui-link ui-link_black']")).click();
                    return pageManager.getProductPage();
                } catch (NoSuchElementException ignored) {
                }
            }
        }
        Assertions.fail("Товар для покупки не найден");
        return null;
    }

}
