package ru.tuxoft.order.enums;

/**
 * Created by Valera on 20.06.2018.
 */
public enum PaymentMethodEnum {
    PAYMENT_IN_SITE("paymentInSite", "Оплата на сайте", "Оплата банковской картой. После завершения оформления заказа Вы будете перенаправлены на страницу банка для оплаты. Также вы можете оплатить заказ в Личном кабинете."),
    PAYMENT_IN_RECEIVE("paymentInReceive", "Оплата при получении", "Оплата банковской картой или наличными (подставляется из информации после выбора доставки)");

    private String value;

    private String text;

    private String comment;

    private PaymentMethodEnum(String value, String text, String comment) {
        this.value = value;
        this.text = text;
        this.comment = comment;
    }

    public String getText() {
        return this.text;
    }

    public String getValue() {
        return this.value;
    }

    public String getComment() {
        return this.comment;
    }
}
