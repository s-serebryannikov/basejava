package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private List<Periods> periodsList;
    private final Link titleLink;

    public Organization(List<Periods> periodsList, Link titleLink) {
        this.periodsList = periodsList;
        this.titleLink = titleLink;
    }

    public List<Periods> getPeriodsList() {
        return periodsList;
    }

    public Link getTitleLink() {
        return titleLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(periodsList, that.periodsList) && Objects.equals(titleLink, that.titleLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodsList, titleLink);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "periodsList=" + periodsList +
                ", titleLink=" + titleLink +
                '}';
    }
}
