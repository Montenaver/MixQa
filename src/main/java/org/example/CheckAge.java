package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CheckAge {
    String expectedExceptionText = "The date is not clear. Try the other format";
    public boolean isAdult(String birthDate) {
        LocalDate dateOfBirth = parseDate(birthDate);
        LocalDate currentDate = LocalDate.now();
        if (!dateOfBirth.isAfter(currentDate)) {
            Period period = Period.between(dateOfBirth, currentDate);
            return period.getYears() >= 18;
        } else {
            throw new IllegalArgumentException(expectedExceptionText);
        }
    }
    private LocalDate parseDate(String birthDate) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("MM-dd-yyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM.dd.yyyy"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("dd/ M/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/ M/dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("M/dd/yyyy"),
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
                DateTimeFormatter.ofPattern("MMMM dd, yyyy"),
                DateTimeFormatter.ofPattern("d MMMM, yyyy"),
                DateTimeFormatter.ofPattern("yyyy, MMMM d"),
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                DateTimeFormatter smartFormatter = formatter.withResolverStyle(ResolverStyle.SMART);
                LocalDate parsedDate = LocalDate.parse(birthDate, smartFormatter);
                String parsedDateStr = parsedDate.format(smartFormatter);
                isChanged(parsedDateStr, birthDate);
                return parsedDate;
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException(expectedExceptionText);
    }
    private void isChanged(String parsedDate, String birthDate){
        if (!parsedDate.equals(birthDate)){
            throw new IllegalArgumentException(expectedExceptionText);
        }
    }
}