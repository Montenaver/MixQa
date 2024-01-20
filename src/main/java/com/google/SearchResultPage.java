package com.google;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class SearchResultPage extends BasePage {
    public static final ElementsCollection visibleArticles = $$x("(//div[@class='notranslate TbwUpd NJjxre iUh30 ojE3Fb'])").filter(visible);
    private static final String ARTICLE_PREVIEWS = ".VwiC3b.yXK7lf.lVm3ye.r025kc.hJNv6b";
    private static final String ARTICLE_WITH_VIDEO_PREVIEWS = ".fzUZNc";
    private static final SelenideElement showingResultsForBlock = $("#fprsl");
    public static final SelenideElement resultStats = $("#result-stats");

    public static String requestHasTypo(String request) {
        if (showingResultsForBlock.is(visible)) {
            request = showingResultsForBlock.getText();
        }
        return request;
    }

    public static int[] matchesInTitleAndPreview(String[] queryWords) {
        int fullMatch = 0;
        int partialMatch = 0;
        int noMatch = 0;

        for (SelenideElement article : visibleArticles) {
            String articleTitle = getTitle(article);
            String articlePreview = findArticlePreview(article);
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

    private static String getTitle(SelenideElement article) {
        article.shouldBe(enabled);
        return article.parent().$("h3").getText().toLowerCase();
    }

    private static String findArticlePreview(SelenideElement article) {
        String preview;
        if (!hasPreview(article) && !hasPreviewWithVideo(article)) {
            return findArticlePreview(article.parent());
        } else if (hasPreviewWithVideo(article)) {
            SelenideElement previewWithVideo = article.parent().$(ARTICLE_WITH_VIDEO_PREVIEWS);
            preview = previewWithVideo.getText().toLowerCase();
        } else if (hasPreview(article)) {
            ElementsCollection articlesPreviews = article.parent().$$(ARTICLE_PREVIEWS);
            if (articlesPreviews.size() == 1) {
                preview = articlesPreviews.get(0).getText().toLowerCase();
            } else {
                preview = null;
            }
        } else {
            preview = null;
        }
        return preview;
    }
    private static boolean hasPreview(SelenideElement article){
        return article.parent().$(ARTICLE_PREVIEWS).is(exist);
    }
    private static boolean hasPreviewWithVideo(SelenideElement article){
        return article.parent().$(ARTICLE_WITH_VIDEO_PREVIEWS).is(exist);
    }
    private static int countWordMatches(String[] queryWords, String title, String preview) {
        int wordMatches = 0;

        for (String word : queryWords) {
            wordMatches += (preview != null && hasMatchesInTitleAndPreview(title, preview, word)) ||
                    (preview == null && hasMatchesInTitle(title, word)) ? 1 : 0;
        }
        return wordMatches;
    }

    private static boolean hasMatchesInTitleAndPreview(String articleTitle, String articlePreview, String word) {
        return (articleTitle.contains(word.toLowerCase()) || articlePreview.contains(word.toLowerCase()));
    }

    private static boolean hasMatchesInTitle(String articleTitle, String word) {
        return articleTitle.contains(word.toLowerCase());
    }

    public static boolean resultIsNull() {
        boolean resultIsNull = false;
        String regex = "\\d+([,.]\\d{3})*";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(resultStats.getText());

        if (matcher.find()) {
            String numberStr = matcher.group().replaceAll("[^\\d]", "");
            double number = Double.parseDouble(numberStr);
            if (number == 0) {
                resultIsNull = true;
            }
        }
        return resultIsNull;
    }
}

