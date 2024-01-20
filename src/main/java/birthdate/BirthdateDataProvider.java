package birthdate;

import org.testng.annotations.DataProvider;

public class BirthdateDataProvider {
    @DataProvider(name="adultTrue")
    protected static Object[][] adultTrue(){
        return new Object[][]{
                {"16.11.1977"},
                {"30.09.1977"},
                {"01.01.2006"},
                {"18.01.2006"},//just get 18
                {"17.01.2006"},//yesterday had 18
                {"29.02.2004"},//leap year
                {"13.05.0001"}//very old guy
        };
    }
    @DataProvider(name="adultFalse")
    protected static Object[][] adultFalse(){
        return new Object[][]{
                {"09.04.2016"},
                {"31.08.2019"},
                {"31.12.2006"},
                {"18.01.2024"},//current date
                {"19.01.2006"},//tomorrow will have 18
        };
    }
    @DataProvider(name="birthdateNegetive")
    protected static Object[][] birthdateNegetive(){
        return new Object[][]{
                {""},//empty entry
                {"abc123"},//not a date
                {"10/15/1992"},//Invalid date format
                {"14 05 2021"},//Invalid date format
                {"Feb 28 1995"},//Invalid date format
                {"15.20.1975"},//невозможная дата
                {"32.12.1984"},//невозможная дата
                {"29.02.2001"},//невозможная дата
                {"30.02.1996"},//невозможная дата
                {"31.09.1961"},//невозможная дата
                {"00.08.1948"},//FALSE 0-й день
                {"25.00.1976"},//FALSE 0-й месяц
                {"01.09.0000"},//FALSE 0-й год
                {"18.07.2034"},//FALSE будущая дата
                {"18.07.4219"}//FALSE будущая дата
        };
    }
}
