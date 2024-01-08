package com.google;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NoResultPage extends BasePage {
    private static final SelenideElement noMatchesMsg = $x("//p[@role]");
    private static final SelenideElement blockOfSuggestions = $x("//div[@class='card-section']//ul");

    public void searchPageIsEmpty(){
        noMatchesMsg.shouldBe(visible);
        blockOfSuggestions.shouldBe(visible);
        logger.info("There is no results for the request.");
    }
}
