package ru.tuxoft.order.enums;

/**
 * Created by Valera on 20.06.2018.
 */
public enum SendTypeEnum {
    MAILSERVICE("mailService","Почтой"),
    COURIERSERVICE("courierService","Курьерской службой"),
    SELFTAKE("selfTake","Самовывоз");

    private String value;

    private String text;

    private SendTypeEnum(String value, String text) {
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
