package com.google;

import org.testng.annotations.Test;


public class GoogleSearchTest extends BaseTest {
     String url = "https://www.google.com/";

    @Test(dataProvider = "request")
    public void searchTest(String request) {
        logger.info("Start test with the request: " + request);
        HomePage home = new HomePage(url);
        home.openHomePage()
                .search(request)
                .checkTitle(request)
                .checkSearchFld(request)
                .checkResultStatistics()
                .checkArticlesMatchRequest(request);
    }
}
