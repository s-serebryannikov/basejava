package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class CompanySection {
    private final String title;
    private final Link titleLink;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String textInfo;

    public CompanySection(String title, String name, String url, LocalDate startDate, LocalDate endDate, String textInfo) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(textInfo, "textInfo must not be null");
        this.title = title;
        this.titleLink = new Link(name,url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.textInfo = textInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(title, that.title) && Objects.equals(titleLink, that.titleLink) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(textInfo, that.textInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, titleLink, startDate, endDate, textInfo);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "title='" + title + '\'' +
                ", titleLink=" + titleLink +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", textInfo='" + textInfo + '\'' +
                '}';
    }
}
