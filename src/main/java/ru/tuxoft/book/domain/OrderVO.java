package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "shippingAddress")
    private String shippingAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "discount")
    private String discount;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "orderVO", cascade = CascadeType.ALL)
    private List<OrderItemVO> orderItemVOList;
}
