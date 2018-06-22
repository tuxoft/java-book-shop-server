package ru.tuxoft.order.enums;


public enum DeliveryServiceTypeEnum {

    COURIER_SERVICE("courierService", "Курьерская служба"),
    MAIL_SERVICE("mailService", "Почтовая служба"),
    PICKUP_POINT("pickupPoint", "Точка выдачи");

    private String value;

    private String text;

    private DeliveryServiceTypeEnum(String value, String text) {
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
