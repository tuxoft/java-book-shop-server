package ru.tuxoft.seller.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.domain.OrderItemVO;
import ru.tuxoft.order.domain.OrderVO;
import ru.tuxoft.order.domain.ShopCityVO;
import ru.tuxoft.order.enums.PaymentMethodEnum;
import ru.tuxoft.order.enums.SendTypeEnum;
import ru.tuxoft.order.enums.StatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEditVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "shop_city_id")
    private ShopCityVO shopCity;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "comment")
    private String comment;

    @Column(name = "send_type")
    @Enumerated(EnumType.STRING)
    private SendTypeEnum sendType;

    @Column(name = "send_org_id")
    private Long sendOrgId;

    @Column(name = "is_age_18")
    private Boolean isAge18 = false;

    @Column(name = "is_take_status_sms")
    private Boolean isTakeStatusSMS = false;

    @Column(name = "is_take_status_email")
    private Boolean isTakeStatusEmail = false;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "to_pay")
    private BigDecimal toPay;

    @Column(name = "pay_for")
    private BigDecimal payFor;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemVO> orderItemList;

    @Column(name = "addr_index")
    private String addrIndex;

    @Column(name = "addr_city")
    private String addrCity;

    @Column(name = "addr_street")
    private String addrStreet;

    @Column(name = "addr_house")
    private String addrHouse;

    @Column(name = "addr_housing")
    private String addrHousing;

    @Column(name = "addr_building")
    private String addrBuilding;

    @Column(name = "addr_room")
    private String addrRoom;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ActionVO> changeHistory;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "order")
    private OrderWorkInfo orderWorkInfo;

}
