package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона"),
    SKYPE("Скайп"),
    MAIL("Почта"),
    LINKED_IN("LINKED_IN"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
