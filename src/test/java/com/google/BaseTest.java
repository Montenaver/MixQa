package com.google;

import com.codeborne.selenide.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    @BeforeTest
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");

        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
}

    @DataProvider(name="request")
    protected static Object[][] requests(){
        return new Object[][]{
                {"cat"},//one word
                {"frog crocodile cat dog"},//with ARTICLE_WITH_VIDEO_PREVIEWS
                {"copyboook peglet"},//2 words with typo
                {"huohohuhohotfytftrsdswwq"},//no result
                {"(*&$#%%)*_)"}//no words
        };
    }

}
