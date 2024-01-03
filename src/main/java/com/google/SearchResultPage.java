package com.google;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchResultPage extends BasePage{
    private static final SelenideElement resultStats = $("#result-stats");
    private static final ElementsCollection articles = $$x("//div[@class='notranslate TbwUpd YmJh3d NJjxre iUh30 ojE3Fb']");
    private static final String ARTICLE_PREVIEWS = ".VwiC3b.yXK7lf.lVm3ye.r025kc.hJNv6b";
    private static final String ARTICLE_WITH_VIDEO_PREVIEWS = ".fzUZNc";
    private static final SelenideElement showingResultsForBlock = $("#fprsl");
    private static final String REGEX_RESULT_STATS = "^[\\p{L}\\s]+(\\d+([,.]\\d{3})*)[\\p{L}\\s]+\\(\\d+[,.]\\d{2}[\\p{L}\\s/]+\\)\\s*$";

    @Step("Check search result page title suits the request")
    public SearchResultPage checkTitle(String request){
        logger.info("Check search result page title suits the request");
        if (!Objects.requireNonNull(title()).contains(request)) {
            throw new IllegalArgumentException(String.format("Page title is incorrect. It should contain the request," +
                    " but it is not: '%s'", Selenide.title()));
        }
        return this;
    }
    @Step("Check search field contain the request")
    public SearchResultPage checkSearchFld(String request){
        logger.info("Check search field contain the request");
        searchFld.shouldBe(visible.because("search field should be visible")).
                shouldHave((text(request).because("the text in search field should be the same as the request")));
        return this;
    }
    @Step("Check result statistics displayed")
    public SearchResultPage checkResultStatistics(){
        //проверяем сообщение о количестве найденных результатов
        logger.info("Check result statistics displayed");
        resultStats.shouldBe(visible).shouldHave(matchText(REGEX_RESULT_STATS));
        return this;
    }
    @Step("Check found articles")
    public void checkArticlesMatchRequest(String request){
        //Проверяем, что текст запроса соответствует найденным статьям (в упрощенной форме, просто делим запрос на слова и ищем совпадения)
        logger.info("Check found articles");
        int foundArticles = articles.filter(visible).size();

        //если нет результатов на странице, проверяем, что отображается страница без результатов
        if (resultIsNull()){
            NoResultPage noResultPage = new NoResultPage();
            noResultPage.searchPageIsEmpty();
        } else {
            //если в запросе только спецсимволы и числа, просто считаем сколько статей вернула программа (не проверяем корректность текста статей)
            if (requestHasWords(request)) {
                if (requestHasTypo()) {//если есть опечатка, берем текст запроса из предложенного Google
                    request = showingResultsForBlock.getText();
                    logger.info("There is a typo in the request. The matches will be count for the request: {}", request);
                }
                //разделяем запрос на слова смотрим, что эти слова встречаюся в заголовке или превью найденных статей
                String[] queryWords = splitRequest(request);
                int[] articlesMatch = matchesInTitleAndPreview(queryWords);

                if (foundArticles != 0) {
                    String result = getString(articlesMatch, foundArticles);
                    //выводим статистику по совпадению найднных статей с запросом
                    logger.info(result);
                }
            } else {
                logger.info("{} articles are displayed for the request: {}",foundArticles, request);
            }
        }
    }
    private static boolean requestHasTypo(){
        return showingResultsForBlock.is(visible);
    }
    private int[] matchesInTitleAndPreview(String[] queryWords){
        int fullMatch = 0;
        int partialMatch = 0;
        int noMatch = 0;

        for (SelenideElement article : articles.filter(visible)){
            String articleTitle = getTitle(article);
            String articlePreview = findArticlePreview(article, articleTitle);
            int wordMatches = countWordMatches(queryWords, articleTitle, articlePreview);

            if (wordMatches == queryWords.length) {
                fullMatch++;
            } else if (wordMatches == 0) {
                noMatch++;
            } else {
                partialMatch++;
            }
        }

        return new int[]{fullMatch, partialMatch, noMatch};
    }
    private static String getTitle(SelenideElement article){
        article.shouldBe(enabled);
        return article.parent().$("h3").getText().toLowerCase();
    }
    private static String findArticlePreview(SelenideElement article, String title){
        String articlePreview;
        if(!article.parent().$(ARTICLE_PREVIEWS).is(exist) && !article.parent().$(ARTICLE_WITH_VIDEO_PREVIEWS).is(exist)) {
            return findArticlePreview(article.parent(), title);
        } else if (article.parent().$(ARTICLE_WITH_VIDEO_PREVIEWS).is(exist)){
            SelenideElement articleWithVideoPreview = article.parent().$(ARTICLE_WITH_VIDEO_PREVIEWS);
            articlePreview = articleWithVideoPreview.getText().toLowerCase();
        }else if (article.parent().$(ARTICLE_PREVIEWS).is(exist)) {
            ElementsCollection articlesPreviews = article.parent().$$(ARTICLE_PREVIEWS);
            if (articlesPreviews.size() == 1) {
                articlePreview = articlesPreviews.get(0).getText().toLowerCase();
            }else{
                articlePreview = null;
                logger.info("There is no preview for the article: {}", title);
            }
        }else{
            articlePreview = null;
            logger.info("There is no preview for the article: {}", title);
        }
        return articlePreview;
    }
    private int countWordMatches(String[] queryWords, String articleTitle, String articlePreview) {
        int wordMatches = 0;

        for (String word : queryWords) {
            wordMatches += (articlePreview != null && searchMatchesInTitleAndPreview(articleTitle, articlePreview, word)) ||
                    (articlePreview == null && searchMatchesInTitle(articleTitle, word)) ? 1 : 0;
        }

        return wordMatches;
    }
    private boolean searchMatchesInTitleAndPreview(String articleTitle, String articlePreview, String word){
        return (articleTitle.contains(word.toLowerCase()) || articlePreview.contains(word.toLowerCase()));
    }
    private boolean searchMatchesInTitle(String articleTitle, String word){
        return articleTitle.contains(word.toLowerCase());
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
    private boolean resultIsNull(){
        boolean resultIsNull = false;
        String regex = "\\d+([,.]\\d{3})*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(resultStats.getText());

        if (matcher.find()) {
            String numberStr = matcher.group().replaceAll("[^\\d]", "");
            double number = Double.parseDouble(numberStr);
            if (number == 0){
                resultIsNull = true;
            }
        }
        return resultIsNull;
    }
}
