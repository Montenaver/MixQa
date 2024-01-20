package birthdate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CheckAge {
    String expectedExceptionText = "Invalid date format. Please use dd.MM.yyyy.";
    public boolean isAdult(String birthDate) {
        LocalDate dateOfBirth = parseDate(birthDate);
        LocalDate currentDate = LocalDate.now();

        if (!dateOfBirth.isAfter(currentDate)) {
            return Period.between(dateOfBirth, currentDate).getYears() >= 18;
        } else {
            throw new IllegalArgumentException(expectedExceptionText);
        }
    }
    private LocalDate parseDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withResolverStyle(ResolverStyle.SMART);
        try {
            LocalDate parsedDate = LocalDate.parse(birthDate, formatter);
            String parsedDateStr = parsedDate.format(formatter);
            isChanged(parsedDateStr, birthDate);
            return parsedDate;
        } catch (DateTimeParseException ignored) {
            throw new IllegalArgumentException(expectedExceptionText);
        }
    }
    private void isChanged(String parsedDate, String birthDate){
        if (!parsedDate.equals(birthDate)){
            throw new IllegalArgumentException(expectedExceptionText);
        }
    }
}