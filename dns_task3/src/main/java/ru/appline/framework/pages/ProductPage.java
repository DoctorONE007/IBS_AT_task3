package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.framework.products.Product;
import java.util.List;

/**
 * Реализация страницы продукта
 */
public class ProductPage extends BasePage {
    @FindBy(xpath = "//h1")
    private WebElement name;
    @FindBy(xpath = "//div[@class='product-card-top__buy']//div[contains(@class,'interactive')]/div[contains(@class,'price')]")
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
    @FindBy(xpath = "//span[@class='cart-link__badge']")
    private WebElement numberInCart;

    /**
     * Сохранение информации о товаре
     * @return ProductPage
     */
    public ProductPage saveInfo(String searchedName) {
        Assertions.assertTrue(waitUtilElementToBeVisible(price),"Цена товара не загрузилась");
        double productPrice = Double.parseDouble(price.getText().split("₽")[0].replaceAll("\\s+", ""));
        int id = Integer.parseInt(idElement.getText().replaceAll("[^0-9]", ""));
        boolean productGuaranty;
        try {
            guaranty.isDisplayed();
            productGuaranty = true;
        } catch (NoSuchElementException ex) {
            productGuaranty = false;
        }
        products.add(new Product(searchedName,name.getText(), productPrice, productGuaranty, description.getText(), id,products.size()));
        return this;
    }

    /**
     * Добавление гарантии
     * @param addYears - сколько лет гарантии
     * @return ProductPage
     */
    public ProductPage addGuaranty( int addYears) {
        Assertions.assertTrue(products.get(products.size()-1).getGuaranty(),"У элемента отсутстует доп. гарантия");
        guaranty.click();
        Assertions.assertTrue(listGuaranty.size() > addYears,"У элемента отсутстует доп. гарантия + " + addYears * 12 + " мес.");
        listGuaranty.get(addYears).click();
        products.get(products.size()-1).setPrice(Double.parseDouble(price.getText().split("₽")[0].replaceAll("\\s+", "")));
        products.get(products.size()-1).setYearsGuaranty(addYears);
        return this;
    }

    /**
     * Добавление товара в корзину
     * @return HomePage
     */
    public HomePage addToCart() {
        int prevNumber;
        try{
            prevNumber=Integer.parseInt(numberInCart.getText());
        }catch (NoSuchElementException ex){
            prevNumber =0;
        }
        Assertions.assertTrue(waitUtilElementToBeClickable(buyButton), "Кнопка добавить в корзину не кликабельна");
        buyButton.click();
        Assertions.assertTrue(waitUtilTextToBePresent(numberInCart,String.valueOf(prevNumber+1)),"Не верное изменение счетчика корзины");
        return pageManager.getHomePage();
    }


}
