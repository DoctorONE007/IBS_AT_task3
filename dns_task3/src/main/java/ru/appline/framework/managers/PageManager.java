package ru.appline.framework.managers;

import ru.appline.framework.pages.CartPage;
import ru.appline.framework.pages.HomePage;

import ru.appline.framework.pages.ProductPage;
import ru.appline.framework.pages.ResultsPage;


/**
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    private HomePage homePage;

    /**
     * Страничка с результатами поиска
     */
    private ResultsPage resultsPage;
    /**
     * Страничка продукта
     */
    private ProductPage productPage;
    /**
     * Страничка корзины
     */
    private CartPage cartPage;


    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link HomePage}
     *
     * @return HomePage
     */
    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    /**
     * Ленивая инициализация {@link ResultsPage}
     *
     * @return ResultsPage
     */
    public ResultsPage getResultsPage() {
        if (resultsPage == null) {
            resultsPage = new ResultsPage();
        }
        return resultsPage;
    }

    /**
     * Ленивая инициализация {@link ProductPage}
     *
     * @return ProductPage
     */
    public ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage();
        }
        return productPage;
    }

    /**
     * Ленивая инициализация {@link CartPage}
     *
     * @return CartPage
     */
    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

}
