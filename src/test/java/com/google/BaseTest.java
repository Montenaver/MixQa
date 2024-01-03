package com.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @DataProvider(name="request")
    protected static Object[][] requests(){
        return new Object[][]{
                {"cat"},
                {"frog crocodile cat dog"},
                {"copyboook peglet"},
                {"huohohuhohotfytftrsdswwq"},
                {"(*&amp;$#%%)*_)"},
        };
    }

}
