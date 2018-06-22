package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.enums.DeliveryServiceTypeEnum;
import ru.tuxoft.order.enums.PaymentMethodEnum;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_methods")
public class PaymentMethodVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_service_type")
    @Enumerated(EnumType.STRING)
    private DeliveryServiceTypeEnum deliveryServiceType;

    @Column(name = "delivery_service_id")
    private Long deliveryServiceId;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

}
