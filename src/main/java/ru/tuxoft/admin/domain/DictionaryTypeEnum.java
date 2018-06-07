package ru.tuxoft.admin.domain;

public enum DictionaryTypeEnum {
    PUBLISHER("publisher"),
    BOOKSERIES("bookSeries"),
    AUTHOR("author"),
    CATEGORY("category"),
    AGELIMIT("ageLimit"),
    CITY("city"),
    LANGUAGE("language"),
    COVERTYPE("coverType");

    String type;

    private DictionaryTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
