package com.google;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GoogleSearchTest extends BaseTest {

//    @Parameters({ "request" })
    @Test(dataProvider = "request")
    public void searchTest(String request) {
        logger.info("Start test with the request: " + request);
        HomePage home = new HomePage();
//        int x = 0;
//        try {
//            // Код, который может вызвать исключение
//            int result = divide(10, 0);
//            System.out.println("Результат: " + result);
//        } catch (ArithmeticException e) {
//            // Обработка исключения, если оно произошло
//            logger.warn("Деление на ноль: {}", e.getMessage());
//        }


        home.openHomePage()
                .search(request)
                .checkTitle(request)
                .checkSearchFld(request)
                .checkResultStatistics()
                .checkArticlesMatchRequest(request);
    }
    public static int divide(int a, int b) {
        // Метод, который может вызвать исключение
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return a / b;
    }
}
