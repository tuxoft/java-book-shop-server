package ru.tuxoft.seller.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.domain.OrderVO;
import ru.tuxoft.seller.domain.enums.WorkStatusEnum;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_work_info")
public class OrderWorkInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "work_status")
    @Enumerated(EnumType.STRING)
    private WorkStatusEnum workStatus;

    @Column(name = "work_status_description")
    private String workStatusDescription;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private SellerVO worker;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderVO order;
}
