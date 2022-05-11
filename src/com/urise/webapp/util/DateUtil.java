package com.urise.webapp.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.time.LocalDate;
import java.time.Month;

@XmlAccessorType(XmlAccessType.FIELD)
public class DateUtil{
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);


    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}


