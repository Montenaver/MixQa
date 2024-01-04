package org.example;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckAge {
    public static void main(String[] args) throws ParseException {
        //Это просто пример метода, который обрабатывает введенную дату рожения и выдает, есть ли пользователю 18 лет
        //Этот код реально работает плохо, но создание функции не было целью данного задания.
        if (args.length > 0) {
            String inputDate = args[0];
            try {
                if (isAdult(inputDate)) {
                    System.out.println("You are adult: you are more than 18 years old");
                } else {
                    System.out.println("You are too young: you are less than 18 years old");
                }
            } catch (ParseException e) {
                System.out.println("Error in date parsing. Make sure the date is in the correct format");
            }
        } else {
            System.out.println("Please provide your birthdate as a command line argument.");
        }
    }

    private static boolean isAdult(String birthDate) throws ParseException {
        LocalDate dateOfBirth = parseDate(birthDate);
        LocalDate currentDate = LocalDate.now();

        int ageInYears = currentDate.minusYears(dateOfBirth.getYear()).getYear();

        return ageInYears >= 18;
    }
    private static LocalDate parseDate(String dateStr) {
        DateTimeFormatter[] formatters = {
                //Я не добавляла всвозможные форматы даты, так как это не было целью данного задания
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("d MMM yyyy")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("The date is not clear. Try the other format");
    }
}