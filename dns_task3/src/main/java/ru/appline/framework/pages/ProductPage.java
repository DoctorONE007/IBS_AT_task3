package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.products.Product;
import java.util.List;

/**
 * Реализация страницы продукта
 */
public class ProductPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement name;
    @FindBy(xpath = "//div[@class='product-card-top__buy']//div[@class='product-buy__price-wrap product-buy__price-wrap_interactive']/div[1]")
    private WebElement price;
    @FindBy(xpath = "//div[@class='additional-sales-tabs__titles-wrap']/div[contains(text(),'Гарантия')]")
    private WebElement guaranty;
    @FindBy(xpath = "//div[@class='price-item-description']/p")
    private WebElement description;
    @FindBy(xpath = "//div[@class='product-card-top__code']")
    private WebElement idElement;
    @FindBy(xpath = "//label[contains(@class,'ui-radio__item product-warranty__item')]")
    private List<WebElement> listGuaranty;
    @FindBy(xpath = "//div[@class='product-buy product-buy_one-line']//button[@class = 'button-ui buy-btn button-ui_passive button-ui_brand']")
    private WebElement buyButton;

    /**
     * Сохранение информации о товаре
     * @param i - номер товара по порядку
     * @return ProductPage
     */
    public ProductPage saveInfo(int i) {
        waitUtilElementToBeVisible(price);
        double productPrice = Double.parseDouble(price.getText().split("₽")[0].replaceAll("\\s+", ""));
        int id = Integer.parseInt(idElement.getText().replaceAll("[^0-9]", ""));
        boolean productGuaranty;
        try {
            guaranty.isDisplayed();
            productGuaranty = true;
        } catch (NoSuchElementException ex) {
            productGuaranty = false;
        }
        products.add(new Product(name.getText(), productPrice, productGuaranty, description.getText(), id));
        return this;
    }

    /**
     * Добавление гарантии
     * @param i - номер товара
     * @param addYears - сколько лет гарантии
     * @return ProductPage
     */
    public ProductPage addGuaranty(int i, int addYears) {
        if (!products.get(i).getGuaranty())
            Assertions.fail("У элемента под номером " + i + " отсутстует доп. гарантия");
        guaranty.click();
        if (listGuaranty.size() <= addYears)
            Assertions.fail("У элемента под номером " + i + " отсутстует доп. гарантия + " + addYears * 12 + " мес.");
        listGuaranty.get(addYears).click();

        products.get(i).setPrice(Double.parseDouble(price.getText().split("₽")[0].replaceAll("\\s+", "")));
        products.get(i).setYearsGuaranty(addYears);
        return this;
    }

    /**
     * Добавление товара в корзину
     * @return HomePage
     */
    public HomePage addToCart() {
        waitUtilElementToBeClickable(buyButton).click();
        return pageManager.getHomePage();
    }


}
