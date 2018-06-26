package ru.tuxoft.order.enums;

/**
 * Created by Valera on 20.06.2018.
 */
public enum SendTypeEnum {
    COURIER_SERVICE("courierService", "Курьерская служба"),
    MAIL_SERVICE("mailService", "Почтовая служба"),
    PICKUP_POINT("pickupPoint", "Точка выдачи");

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
