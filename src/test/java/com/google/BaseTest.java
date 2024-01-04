package com.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

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
