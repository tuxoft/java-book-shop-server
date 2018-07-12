package ru.tuxoft.seller.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.domain.OrderVO;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "history_actions")
public class ActionVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "change_field")
    private String changeField;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private SellerVO worker;

    @Column(name = "change_date")
    private Date changeDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEditVO order;

}
