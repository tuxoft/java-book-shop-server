package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

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
    private String paymentMethod;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "discount")
    private String discount;

    @Column(name = "to_pay")
    private BigDecimal toPay;

    @Column(name = "pay_for")
    private BigDecimal payFor;

    @Column(name = "status")
    private String status;

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

}
