package com.google;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;


public class GoogleSearchTest{
    private static final SelenideElement searchFld = $("#APjFqb");

    @Test(dataProvider = "baseRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void baseSearchTest(String request) {
        HomePage home = new HomePage(Constants.URL);
        home.openHomePage().search(request);

        checkTitle(request);
        checkSearchFld(request);
        checkResultStatistics();
        checkArticlesMatchRequest(request);
    }
    @Test(dataProvider = "noResultRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void noResultRequestTest(String request) {
        HomePage home = new HomePage(Constants.URL);
        home.openHomePage().search(request);

        checkTitle(request);
        checkSearchFld(request);
        checkResultStatistics();
        checkArticlesNotFound();
    }

    @Test(dataProvider = "noWordsRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void noWordsRequestTest(String request) {
        HomePage home = new HomePage(Constants.URL);
        home.openHomePage().search(request);

        checkTitle(request);
        checkSearchFld(request);
        checkResultStatistics();

        checkArticlesFound(request);
    }
    @Test(dataProvider = "emptyRequest", dataProviderClass = GoogleSearchDataProvider.class)
    public void emptyRequestTest(String request) {
        HomePage home = new HomePage(Constants.URL);
        home.openHomePage().search(request);
        checkUrlOpened();
    }

    @Step("Check search result page title suits the request")
    public void checkTitle(String request){
        if (!Objects.requireNonNull(title()).contains(request)) {
            throw new IllegalArgumentException(String.format("Page title is incorrect. It should contain the request," +
                    " but it is not: '%s'", Selenide.title()));
        }
    }
    @Step("Check search field contain the request")
    public void checkSearchFld(String request){
        searchFld.shouldBe(visible.because("search field should be visible")).
                shouldHave((text(request).because("the text in search field should be the same as the request")));
    }
    @Step("Check result statistics displayed")
    public void checkResultStatistics(){
        SearchResultPage.resultStats.shouldBe(visible).shouldHave(matchText(Constants.REGEX_RESULT_STATS));
    }
    @Step("Check user was not redirected to ResultPage after empty request.")
    public void checkUrlOpened() {
        String currentUrl = url();
        Assert.assertEquals(currentUrl, Constants.URL, "User was redirected. Current URL was changed");
    }
    @Step("Check articles for the request are found")
    public void checkArticlesFound(String request){
        if (!SearchResultPage.requestHasWords(request) && !SearchResultPage.resultIsNull()) {
            SearchResultPage.visibleArticles.shouldHave(sizeGreaterThan(0));
        } else if (!SearchResultPage.requestHasWords(request)){
            checkArticlesNotFound();
        }
    }
    @Step("Check articles for the request are not found")
    public void checkArticlesNotFound(){
        SearchResultPage.visibleArticles.isEmpty();
    }
    @Step("Check found articles match the request")
    public void checkArticlesMatchRequest(String request){
        int foundArticles = SearchResultPage.visibleArticles.size();

        if (!SearchResultPage.resultIsNull() && SearchResultPage.requestHasWords(request)){
            request = SearchResultPage.requestHasTypo(request);
            String[] queryWords = SearchResultPage.splitRequest(request);
            int[] articlesMatch = SearchResultPage.matchesInTitleAndPreview(queryWords);

            if (foundArticles != 0) {
                String result = getString(articlesMatch, foundArticles);
                SearchResultPage.logger.info(result);
            }
        } else if (!SearchResultPage.resultIsNull()) {
            checkArticlesFound(request);
        } else {
            checkArticlesNotFound();
        }
    }
    private static String getString(int[] articlesMatch, int foundArticles) {
        double fullMatchPrc = (double) articlesMatch[0] / foundArticles * 100;
        double partialMatchPrc = (double) articlesMatch[1] / foundArticles * 100;
        double noMatchPrc = (double) articlesMatch[2] / foundArticles * 100;
        return """
                %.1f%% of articles fully suit the request.
                %.1f%% of articles partially suit the request.
                %.1f%% of articles do not suit the request.
                """.formatted(fullMatchPrc, partialMatchPrc, noMatchPrc);
    }
}
