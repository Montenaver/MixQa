package com.google;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    private String url = "https://www.google.com/";
    private final SelenideElement searchBtn = $x("//div[@class='FPdoLc lJ9FBc']//input[@name='btnK']");

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
