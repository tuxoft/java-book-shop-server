package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "basket_items")
public class BasketItemVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookVO book;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private BasketVO basketVO;

    @Column(name = "count")
    private Integer count;
}
