package com.google;

import org.testng.annotations.Test;


public class GoogleSearchTest extends BaseTest {
     String url = "https://www.google.com/";

    @Test(dataProvider = "baseRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void baseSearchTest(String request) {
        logger.info("Start baseRequestTest with the request: " + request);
        HomePage home = new HomePage(url);
        home.openHomePage()
                .search(request)
                .checkTitle(request)
                .checkSearchFld(request)
                .checkResultStatistics()
                .checkArticlesMatchRequest(request);
    }
    @Test(dataProvider = "noResultRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void noResultRequestTest(String request) {
        logger.info("Start noResultRequestTest with the request: " + request);
        HomePage home = new HomePage(url);
        home.openHomePage()
                .search(request)
                .checkTitle(request)
                .checkSearchFld(request)
                .checkResultStatistics();
        new NoResultPage().searchPageIsEmpty();
    }

    @Test(dataProvider = "noWordsRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void noWordsRequestTest(String request) {
        logger.info("Start noWordsRequestTest with the request: " + request);
        HomePage home = new HomePage(url);
        home.openHomePage()
                .search(request)
                .checkTitle(request)
                .checkSearchFld(request)
                .checkResultStatistics()
                .articlesFound(request);
    }
    @Test(dataProvider = "emptyRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void emptyRequestTest(String request) {
        logger.info("Start emptyRequestTest with the request: " + request);
        HomePage home = new HomePage(url);
        home.openHomePage()
                .search(request);
        home.isOpened();
    }
}
