package com.google;

import org.testng.annotations.DataProvider;

public class GoogleSearchDataProvider {
    @DataProvider(name="baseRequest")
    protected static Object[][] baseRequest(){
        return new Object[][]{
                {"cat"},//one word
                {"frog crocodile cat dog"},//with ARTICLE_WITH_VIDEO_PREVIEWS
                {"copyboook peglet"},//2 words with typo
                {"seeson flight tickes"},//2 words with typo
                {"huohohuhohotfytftrsdswwq"},//no result
                {"(*&$#%%)*_)"}//no words
        };
    }
    @DataProvider(name="noResultRequest")
    protected static Object[][] noResultRequest() {
        return new Object[][]{
                {"huohohuhohotfytftrsdswwq"},//no result
                {"huhuishfhs fsfs sdffjjif"}//no result
        };
    }
    @DataProvider(name="noWordsRequest")
    protected static Object[][] noWordsRequest() {
        return new Object[][]{
                {"(*&$#%%)*_)"},//no words
                {"423423 3235 534"}//no words
        };
    }
    @DataProvider(name="emptyRequest")
    protected static Object[][] emptyRequest() {
        return new Object[][]{
                {""},//null
                {"     "}//spaces
        };
    }
}
