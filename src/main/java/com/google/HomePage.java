package com.google;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private final String url;

    public HomePage(String url) {
        this.url = url;
    }
    public HomePage openHomePage() {
        open(this.url);
        return this;
    }
    public SearchResultPage search(String request) {
        searchFld.shouldBe(visible);
        searchFld.setValue(request).pressEnter();
        return new SearchResultPage();
    }
}
