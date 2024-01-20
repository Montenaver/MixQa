package birthdate;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdulthoodTest{
    @Test(dataProvider = "adultTrue",
            dataProviderClass = BirthdateDataProvider.class)
    public void checkAgeAdultTest(String date){
        CheckAge checkAge = new CheckAge();
        date = makeDateActual(date);
        Assert.assertTrue(checkAge.isAdult(date));
    }
    @Test(dataProvider = "adultFalse",
            dataProviderClass = BirthdateDataProvider.class)
    public void checkAgeNotAdultTest(String date){
        CheckAge checkAge = new CheckAge();
        date = makeDateActual(date);
        Assert.assertFalse(checkAge.isAdult(date));
    }
    @Test(dataProvider = "birthdateNegetive",
            dataProviderClass = BirthdateDataProvider.class,
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Invalid date format. Please use dd.MM.yyyy.")
    public void checkAgeNegativeTest(String date){
        CheckAge checkAge = new CheckAge();
        checkAge.isAdult(date);
    }
    private static String makeDateActual(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            parsedDate = addPeriod(parsedDate);
            return parsedDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The test date is incorrect");
        }
    }
    private static LocalDate addPeriod(LocalDate birthdate){
        LocalDate currentDate = LocalDate.now();
        LocalDate testCreationDate = LocalDate.of(2024, 01, 18);
        Period period = Period.between(currentDate, testCreationDate);

        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();

        return birthdate.minusYears(years).minusMonths(months).minusDays(days);
    }
}
