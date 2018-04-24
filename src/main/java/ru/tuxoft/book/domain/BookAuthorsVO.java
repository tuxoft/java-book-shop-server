package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_authors")
public class BookAuthorsVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookVO book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorVO author;

    @Column(name = "position")
    private Integer position;

}
