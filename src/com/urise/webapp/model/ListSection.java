package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<String> content;

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> elements) {
        Objects.requireNonNull(elements, "uuid must not be null");
        this.content = elements;
    }

    public List<String> getContent() {
        return content;
    }

    public void addContent(String item){
        content.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String element : content) {
            sb.append(element).append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
