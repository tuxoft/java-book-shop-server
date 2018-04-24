package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookVO book;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderVO orderVO;

    @Column(name = "count")
    private Integer count;
}
