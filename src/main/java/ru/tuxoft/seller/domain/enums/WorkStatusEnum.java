package ru.tuxoft.seller.domain.enums;

public enum WorkStatusEnum {
    NEW("new","Новый"),
    INWORK("inWork","В работе"),
    FINISH("finish","Завершен"),
    DEFER("defer","Отложен");

    private String value;

    private String text;

    private WorkStatusEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String getValue() {
        return this.value;
    }
}
