package ru.appline.framework.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.DriverManager;
import ru.appline.framework.managers.PageManager;
import ru.appline.framework.managers.TestPropManager;
import ru.appline.framework.products.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс всех страничек
 */
public class BasePage {

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     *
     * @see PageManager
     */
    protected PageManager pageManager = PageManager.getPageManager();


    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     *
     * @see WebDriverWait
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 7, 1000);


    /**
     * Менеджер properties
     *
     * @see TestPropManager#getTestPropManager()
     */
    private final TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * List всех добавленных продуктов
     */
    protected static ArrayList<Product> products = new ArrayList<>();

    protected static ArrayList<Product> deletedProducts = new ArrayList<>();


    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     * Подробнее можно просмотреть в класс {@link PageFactory}
     *
     * @see FindBy
     * @see PageFactory
     * @see PageFactory#initElements(WebDriver, Object)
     */
    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }


    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     * @see WebDriverWait
     * @see org.openqa.selenium.support.ui.FluentWait
     * @see org.openqa.selenium.support.ui.Wait
     * @see ExpectedConditions
     */
    protected boolean waitUtilElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    /**
     * Явное ожидание того что элемент станет видемым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected boolean waitUtilElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    /**
     * Явное ожидание что в элементе содержится текст
     * @param element - веб элемент текст которого мы ожидаем что будет  виден на странице
     * @param text - текс для проверки
     * @return boolean
     */
    protected boolean waitUtilTextToBePresent(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    /**
     * Явное ожидание что элемент пропал
     * @param element - веб элемент который мы ожидаем что пропадет
     * @return
     */
    protected boolean waitUtilElementNotToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    /**
     * Явное ожидание появления элемента с текстом в List
     * @param list - все элементы страницы
     * @param text - текст, который должен быть
     * @return
     */
    protected boolean waitUtilElementToBeVisibleInList(List<WebElement> list, String text) {
        for (WebElement element : list)
            if (waitUtilTextToBePresent(element, text))
                return true;

        return false;
    }

}
