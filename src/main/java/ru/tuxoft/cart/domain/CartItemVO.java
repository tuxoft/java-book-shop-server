package ru.tuxoft.cart.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.cart.domain.CartVO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItemVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookVO book;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartVO cart;

    @Column(name = "count")
    private Integer count;

    public CartItemVO(CartVO cartVO, BookVO bookVO, int count) {
        this.cart = cartVO;
        this.book = bookVO;
        this.count = count;
    }
}
