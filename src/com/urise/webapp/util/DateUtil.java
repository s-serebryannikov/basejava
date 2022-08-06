package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil{
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate getLocalDate(String date) {
        if (date.equals("Сейчас") || (date == null || date.trim().length() == 0))  {
            return NOW;
        }
        YearMonth yM = YearMonth.parse(date, DATE_TIME_FORMATTER);
        return LocalDate.of(yM.getYear(), yM.getMonth(), 1);
    }

    public static String getStringDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_TIME_FORMATTER);
    }
}


