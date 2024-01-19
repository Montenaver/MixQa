package birthdate;

import com.google.BaseTest;
import org.example.CheckAge;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdulthoodTest extends BaseTest {
    @Test(dataProvider = "birthdatePositive",
            dataProviderClass = BirthdateDataProvider.class)
    public void checkAgePositiveTest(String date, boolean expectedResult){
        CheckAge checkAge = new CheckAge();
        date = makeDateActual(date);
        Assert.assertEquals(checkAge.isAdult(date), expectedResult);
    }
    @Test(dataProvider = "birthdateNegetive",
            dataProviderClass = BirthdateDataProvider.class,
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The date is not clear. Try the other format")
    public void checkAgeNegativeTest(String date){
        CheckAge checkAge = new CheckAge();
        checkAge.isAdult(date);
    }
    private static String makeDateActual(String date) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("MM-dd-yyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM.dd.yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("M/dd/yyyy"),
                DateTimeFormatter.ofPattern("dd/ M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/ M/dd"),
                DateTimeFormatter.ofPattern("dd MM yyyy"),
                DateTimeFormatter.ofPattern("MM dd yyyy"),
                DateTimeFormatter.ofPattern("yyyy MM dd"),
                DateTimeFormatter.ofPattern("MMM dd yyyy"),
                DateTimeFormatter.ofPattern("dd MM yyyy"),
                DateTimeFormatter.ofPattern("yyyy MMM dd"),
                DateTimeFormatter.ofPattern("dd/M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/M/dd"),
                DateTimeFormatter.ofPattern("MMM dd yyyy"),
                DateTimeFormatter.ofPattern("dd MMM yyyy"),
                DateTimeFormatter.ofPattern("yyyy MMM d"),
                DateTimeFormatter.ofPattern("MMM d, yyyy"),
                DateTimeFormatter.ofPattern("d MMM, yyyy"),
                DateTimeFormatter.ofPattern("yyyy, MMM d"),
                DateTimeFormatter.ofPattern("MMM-dd-yyyy"),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MMM-dd"),
                DateTimeFormatter.ofPattern("MMMM d, yyyy"),
                DateTimeFormatter.ofPattern("d MMMM, yyyy"),
                DateTimeFormatter.ofPattern("yyyy, MMMM d"),
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, formatter);
                parsedDate = addPeriod(parsedDate);
                return parsedDate.format(formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("The test date is incorrect");
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
