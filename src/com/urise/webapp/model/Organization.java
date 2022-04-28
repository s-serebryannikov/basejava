package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.*;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Position> positions;
    private final Link homePage;

    public Organization(Link homePage, List<Position> periodsList) {
        this.homePage = homePage;
        this.positions = periodsList;
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public List<Position> getPositions() {
        return positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(positions, that.positions) && Objects.equals(homePage, that.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions, homePage);
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + "," + positions + ')';
    }

    public static class Position implements Serializable{
        private final String title;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String discription;

        public Position(int startYear, Month startMonth, String title, String discription) {
            this(of(startYear, startMonth), NOW, title, discription);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String discription) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, discription);
        }


        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.discription = description;
        }


        public String getTitle() {
            return title;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDiscription() {
            return discription;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(title, position.title) &&
                    Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(discription, position.discription);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, startDate, endDate, discription);
        }

        @Override
        public String toString() {
            return "Position(" + startDate + ',' + "endDate" + ',' + endDate + ',' + title + ',' + discription + ')';
        }
    }
}
