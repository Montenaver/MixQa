package birthdate;

import org.testng.annotations.DataProvider;

public class BirthdateDataProvider {
    @DataProvider(name="birthdatePositive")
    protected static Object[][] birthdatePositive(){
        return new Object[][]{
                {"03-27-1973", true},
                {"08-31-2008", false},
                {"15-05-1990", true},
                {"10-08-2007", false},
                {"1945-08-28", true},
                {"2008-01-01", false},
                {"11.28.1994", true},
                {"01.13.2013", false},
                {"16.11.1977", true},
                {"31.12.2016", false},
                {"1980.06.20", true},
                {"2023.04.23", false},
                {"10/15/1992", true},
                {"02/19/2018", false},
                {"2004/12/31", true},
                {"2012/12/31", false},
                {"7/18/1987", true},
                {"1/26/2021", false},
                {"13/8/1955", true},
                {"31/1/2010", false},
                {"2004/2/17", true},
                {"2009/2/17", false},
                {"6/23/2002", true},
                {"4/30/2012", false},
                {"13/ 2/1972", true},
                {"23/ 4/2016", false},
                {"2005/ 2/17", true},
                {"2010/ 7/31", false},
                {"22 12 1992", true},
                {"14 05 2021", false},
                {"11 27 1998", true},
                {"10 28 2007", false},
                {"2006 01 01", true},
                {"2007 01 11", false},
                {"Feb 28 1995", true},
                {"Jan 31 2006", false},
                {"29 Sep 1963", true},
                {"12 Mar 2008", false},
                {"1997 Nov 16", true},
                {"2016 May 5", false},
                {"Dec 15, 1985", true},
                {"Jul 9, 2020", false},
                {"22 Dec, 1982", true},
                {"23 Aug, 2019", false},
                {"1986, Apr 14", true},
                {"2021, Nov 6", false},
                {"Dec-04-1966", true},
                {"Oct-24-2013", false},
                {"01-Jan-2000", true},
                {"07-Sep-2011", false},
                {"2001-Jun-20", true},
                {"2017-May-13", false},
                {"June 4, 1999", true},
                {"March 25, 2013", false},
                {"3 October, 1959", true},
                {"1 January, 2024", false},
                {"2001, December 13", true},
                {"2014, April 26", false},
                {"1900-Mar-31", true},
                {"12 Feb, 1345", true},
                {"2004/2/29", true},//високосный год
                {"2024-01-18", false},//текущая дата
                {"May 13 0001", true}//очень давно
        };
    }

    @DataProvider(name="birthdateNegetive")
    protected static Object[][] birthdateNegetive(){
        return new Object[][]{
                {""},//FALSE
                {"abc123"},//FALSE
                {"15.20.1975"},//FALSE невозможная дата
                {"32 Dec, 1984"},//FALSE невозможная дата
                {"29.02.2001"},//FALSE невозможная дата
                {"1996/2/30"},//FALSE невозможная дата
                {"1961-09-31"},//FALSE невозможная дата
                {"Aug-00-1948"},//FALSE 0-й день
                {"00.25.1976"},//FALSE 0-й месяц
                {"1 September, 0000"},//FALSE 0-й год
                {"2024-07-18"},//FALSE будущая дата
                {"July 18, 2219"}//FALSE будущая дата
        };
    }
}
