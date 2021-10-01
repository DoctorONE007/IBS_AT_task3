package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.products.Product;

import java.util.List;

/**
 * Реализация страницы корзины
 */
public class CartPage extends BasePage {
    //переписать xpath
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
    @FindBy(xpath = "//a[@class='cart-items__product-name-link']")
    private List<WebElement> listNames;
    @FindBy(xpath = "//div[@class='group-tabs']//span[@class='restore-last-removed']")
    private WebElement restore;
    @FindBy(xpath = "//span[@class='cart-link__badge']")
    private WebElement numberInCart;

    public CartPage() {
    }

    /**
     * Проверка соответстия суммы в корзине
     *
     * @return CartPage
     */
    public CartPage checkSum() {
        double cartSum = Double.parseDouble(cartSumElement.getText().split("Р")[0].replaceAll("\\s+", ""));
        double upperCartSum = Double.parseDouble(upperCartSumElement.getText().replaceAll("[^0-9]", ""));
        double productsSum = 0;
        for (Product product : products)
            productsSum += product.getPrice() * product.getQuantity();
        Assertions.assertFalse(cartSum != productsSum || upperCartSum != productsSum, "Сумма в корзине не соответствует сумме покупок");
        return this;
    }

    /**
     * Удаление стандартной скидки в корзине
     *
     * @return CartPage
     */
    public CartPage deleteDiscount() {
        if (waitUtilElementToBeClickable(discountButton)) {
            String sum = cartSumElement.getText();
            discountButton.click();
            Assertions.assertFalse(waitUtilTextToBePresent(cartSumElement, sum), "Скидка не убралась в основном меню");
            Assertions.assertFalse(waitUtilTextToBePresent(upperCartSumElement, sum), "Скидка не убралась в вверхнем меню");
        }
        return this;
    }

    /**
     * Проверка соответсвия гарантии и суммы
     *
     * @return CartPage
     */
    public CartPage checkGuarantyAndPrice() {
        double price;
        for (Product product : products) {
            price = Double.parseDouble(listPrices.get(products.indexOf(product)).getText().replaceAll("[^0-9]", ""));
            if (product.getGuaranty()) {
                switch (product.getYearsGuaranty()) {
                    case 0:
                        Assertions.assertEquals(guarantyElements.get(products.indexOf(product)).getText(), "Без доп. гарантии", "Меню гарантии не совпадает");
                        break;
                    case 1:
                        Assertions.assertEquals(guarantyElements.get(products.indexOf(product)).getText(), "+ 12 мес.", "Меню гарантии не совпадает");
                        price += Double.parseDouble(guarantyPriceElements.get(products.indexOf(product)).getText().replaceAll("[^0-9]", ""));
                        break;
                    case 2:
                        Assertions.assertEquals(guarantyElements.get(products.indexOf(product)).getText(), "+ 24 мес.", "Меню гарантии не совпадает");
                        price += Double.parseDouble(guarantyPriceElements.get(products.indexOf(product)).getText().replaceAll("[^0-9]", ""));
                        break;


                }
                Assertions.assertEquals(price, product.getPrice(), "Цена продукта не совпадает");
            }
        }
        return this;
    }

    /**
     * Удаление товара из корзины
     *
     * @param name - имя заданное в поиске
     * @return CartPage
     */
    public CartPage delete(String name) {
        boolean isSuccess = false;
        for (Product product : products) {
            if (product.getSearchedName().equals(name)) {
                deletedProducts.add(product);
                for (WebElement element : listNames) {
                    if (element.getText().contains(name)) {
                        WebElement deleteButton = element.findElement(By.xpath("./../..//button[contains(.,'Удалить')]"));
                        Assertions.assertTrue(waitUtilElementToBeClickable(deleteButton), "Кнопка удалить не активна");
                        deleteButton.click();
                        Assertions.assertFalse(waitUtilElementNotToBeVisible(element), "Элемент не удален");
                        products.remove(product);
                        isSuccess = true;
                        break;
                    }
                }
                Assertions.assertTrue(isSuccess, "Элемент для удаления не найден");
                break;
            }
        }
        Assertions.assertTrue(isSuccess, "Элемента с таким именем нет в массиве");
        checkSum();
        return this;
    }

    /**
     * Добавление существующего товара
     *
     * @param name - имя заданное в поиске
     * @return CartPage
     */
    public CartPage add(String name) {
        boolean isSuccess = false;
        for (WebElement element : listNames) {
            if (element.getText().contains(name)) {
                WebElement addButton = element.findElement(By.xpath("./../../../../..//i[@class='count-buttons__icon-plus']"));
                Assertions.assertTrue(waitUtilElementToBeClickable(addButton), "Кнопка добавить не активна");
                int prevNumber = Integer.parseInt(numberInCart.getText());
                addButton.click();
                Assertions.assertTrue(waitUtilTextToBePresent(numberInCart, String.valueOf(prevNumber + 1)), "Не верное изменение счетчика корзины");
                isSuccess = true;
                break;
            }
        }
        Assertions.assertTrue(isSuccess, "Не добавился +1 товар");
        isSuccess = false;
        for (Product product : products) {
            if (product.getSearchedName().equals(name)) {
                product.setQuantity(product.getQuantity() + 1);
                isSuccess = true;
                break;
            }
        }
        Assertions.assertTrue(isSuccess, "Не найден товар в массиве");
        return this;

    }

    /**
     * Возвращение удаленного ранее товара
     *
     * @return CartPage
     */
    public CartPage addBack() {
        Assertions.assertTrue(waitUtilElementToBeClickable(restore), "Кнопка восстановить не кликабельна");
        int prevNumber = Integer.parseInt(numberInCart.getText());
        restore.click();
        Product lastDeleted = deletedProducts.get(deletedProducts.size() - 1);
        Assertions.assertTrue(waitUtilTextToBePresent(numberInCart, String.valueOf(prevNumber + lastDeleted.getQuantity())), "Не верное изменение счетчика корзины");
        Assertions.assertTrue(waitUtilElementToBeVisibleInList(listNames, lastDeleted.getSearchedName()));
        products.add(lastDeleted.getIndexInArray(), lastDeleted);
        checkSum();
        return this;
    }

}
