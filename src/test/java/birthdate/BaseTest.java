package birthdate;

import org.testng.annotations.DataProvider;

public class BaseTest {
    @DataProvider(name="birthdate")
    protected static Object[][] birthdate(){
        return new Object[][]{
                {1,"2000-11-02"},
                {2,"2020-11-02"},
                {3,"2000-11-02"},

        };
    }
}
