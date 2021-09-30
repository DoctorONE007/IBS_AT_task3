package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.products.Product;

import java.util.List;

/**
 * Реализация страницы корзины
 */
public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@id='total-amount']//span[@class='price__current']")
    private WebElement cartSumElement;
    @FindBy(xpath = "//div[@class='total-amount-toggler payment-method-discount-toggler']//span[@class ='total-amount-toggler__slider']")
    private WebElement discountButton;
    @FindBy(xpath = "//span[@class='cart-link__price']")
    private WebElement upperCartSumElement;
    @FindBy(xpath = "//div[@class='cart-items__product']")
    private List<WebElement> listCartProducts;
    @FindBy(xpath = "//div[@class='cart-items__product']//span[@class='price__current']")
    private List<WebElement> listPrices;
    @FindBy(xpath = "//div[@class='slider']//span[contains(@class,'checked')]")
    private List<WebElement> guarantyElements;
    @FindBy(xpath = "//div[@class='slider']//span[contains(@class,'checked')]/../..//span[@class='additional-warranties-row__price']")
    private List<WebElement> guarantyPriceElements;
    @FindBy(xpath = "//div[@class='cart-items__product-code']")
    private List<WebElement> listProductCodes;
    @FindBy(xpath = "(//span[@class='restore-last-removed'])[2]")
    private WebElement restore;

    private int index;
    private Product savedProduct;

    public CartPage() {
    }

    /**
     * Проверка соответстия суммы в корзине
     * @return CartPage
     */
    public CartPage checkSum() throws InterruptedException {
        Thread.sleep(5000);
        waitUtilElementToBeVisible(cartSumElement);
        double cartSum = Double.parseDouble(cartSumElement.getText().replaceAll("\\s+", "").split("₽")[0]);
        waitUtilElementToBeVisible(upperCartSumElement);
        double upperCartSum = Double.parseDouble(upperCartSumElement.getText().replaceAll("\\s+", ""));
        double productsSum = 0;
        for (Product product : products) {
            productsSum += product.getPrice() * product.getQuantity();
        }
        if (cartSum != productsSum || upperCartSum != productsSum)
            Assertions.fail("Сумма в корзине не соответствует сумме покупок");

        return this;
    }

    /**
     * Удаление стандвртной скидки в корзине
     * @return CartPage
     */
    public CartPage deleteDiscount() {
        try {
            waitUtilElementToBeClickable(discountButton).click();
        } catch (NoSuchElementException ignored) {
        }
        return this;
    }

    /**
     * Проверка соответсвия гарантии и суммы
     * @return CartPage
     */
    public CartPage checkGuarantyAndPrice() {
        double price;
        int i = 0;
        for (Product product : products) {
            price = Double.parseDouble(listPrices.get(products.indexOf(product)).getText().split("₽")[0].replaceAll("\\s+", ""));
            if (product.getGuaranty()) {
                switch (product.getYearsGuaranty()) {
                    case 0:
                        if (!guarantyElements.get(i).getText().equals("Без доп. гарантии"))
                            Assertions.fail("Меню гарантии не совпадает");
                        break;
                    case 1:
                        if (!guarantyElements.get(i).getText().equals("+ 12 мес."))
                            Assertions.fail("Меню гарантии не совпадает");
                        price += Double.parseDouble(guarantyPriceElements.get(i).getText().split("₽")[0].replaceAll("\\s+", ""));
                        if (product.getPrice() != price)
                            Assertions.fail("Цена продукта не совпадает");
                        break;
                    case 2:
                        if (!guarantyElements.get(i).getText().equals("+ 24 мес."))
                            Assertions.fail("Меню гарантии не совпадает");
                        price += Double.parseDouble(guarantyPriceElements.get(i).getText().split("₽")[0].replaceAll("\\s+", ""));
                        if (product.getPrice() != price)
                            Assertions.fail("Цена продукта не совпадает");
                        break;


                }
                i++;
            }
        }
        return this;
    }

    /**
     * Удаление товара из корзины
     * @param id - артикул товара
     * @return CartPage
     */
    public CartPage delete(int id) throws InterruptedException {
        for (Product product : products) {
            if (product.getId() == id) {
                if (product.getQuantity() > 1) {
                    product.setQuantity(product.getQuantity() - 1);
                    break;
                }
                savedProduct = product;
                index = products.indexOf(product);
                products.remove(product);
                break;
            }

        }
        for (WebElement element : listProductCodes) {
            if (Integer.parseInt(element.getText()) == id) {
                element.findElement(By.xpath("./../../../..//button[@class='count-buttons__button count-buttons__button_minus']")).click();
                break;
            }
        }
        Thread.sleep(5000);
        for (WebElement element : listProductCodes)
            if (Integer.parseInt(element.getText()) == id)
                Assertions.fail("Элемент не удален");


        return this;
    }

    /**
     * Добавление существующего товара
     * @param id - артикул товара
     * @return CartPage
     */
    public CartPage add(int id) throws InterruptedException {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setQuantity(product.getQuantity() + 1);
                break;
            }
        }
        for (WebElement element : listProductCodes) {
            if (Integer.parseInt(element.getText()) == id) {
                element.findElement(By.xpath("./../../../..//button[@class='count-buttons__button count-buttons__button_plus']")).click();
                break;
            }
        }
        Thread.sleep(10000);
        return this;

    }

    /**
     * Возвращение удаленного ранее товара
     * @return CartPage
     */
    public CartPage addBack() {
        restore.click();
        products.add(index, savedProduct);

        for (Product product : products) {
            System.out.println(product.getPrice() * product.getQuantity());
        }

        return this;
    }

}
