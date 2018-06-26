package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.enums.PaymentMethodEnum;
import ru.tuxoft.order.enums.SendTypeEnum;

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

    @Column(name = "send_type")
    @Enumerated(EnumType.STRING)
    private SendTypeEnum sendType;

    @Column(name = "send_org_id")
    private Long sendOrgId;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

}
