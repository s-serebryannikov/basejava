package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate of(int year, Month month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return LocalDate.of(year, month, 1);
    }
}
