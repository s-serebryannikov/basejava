package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Periods {
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String textInfo;

    public Periods(String title, LocalDate startDate, LocalDate endDate, String textInfo) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.textInfo = textInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periods periods = (Periods) o;
        return Objects.equals(title, periods.title) && Objects.equals(startDate, periods.startDate) && Objects.equals(endDate, periods.endDate) && Objects.equals(textInfo, periods.textInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate, textInfo);
    }

    @Override
    public String toString() {
        return "Periods{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", textInfo='" + textInfo + '\'' +
                '}';
    }
}
