package ru.tuxoft.order.enums;

/**
 * Created by Valera on 20.06.2018.
 */
public enum StatusEnum {
    PAYD("payd","Оплачено"),
    SHIPPING("shipping","Отправлено"),
    DELIVERY("delivery","Доставлено"),
    UNPAID("unPayd","Не оплачено"),
    CANCELED("canceled","Отменен");

    private String value;

    private String text;

    private StatusEnum(String value, String text) {
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
