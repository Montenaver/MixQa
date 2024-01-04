package com.google;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private final String url;

    public HomePage(String url) {
        this.url = url;
    }
    @Step("Open Google Home Page")
    public HomePage openHomePage() {
        open(this.url);
        return this;
    }
    @Step("Enter request for searching")
    public SearchResultPage search(String request) {
        searchFld.shouldBe(visible);
        searchFld.setValue(request).pressEnter();
        return new SearchResultPage();
    }
}
