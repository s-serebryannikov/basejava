package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    List<String> elements = new ArrayList<>();

    public ListSection(String elements) {
        Objects.requireNonNull(elements, "uuid must not be null");
        this.elements.add(elements);
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "elements=" + elements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
