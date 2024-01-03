package com.google;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    protected final SelenideElement searchFld = $("#APjFqb");
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    protected static boolean requestHasWords(String request){
        return request.matches(".*[a-zA-Z].*");
    }
    protected static String[] splitRequest(String request){
        String cleanedRequest = request.replaceAll("[^a-zA-Z\\s]", "");
        return cleanedRequest.split("\\s+");
    }
}
