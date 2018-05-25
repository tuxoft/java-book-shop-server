package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.BookSeriesDto;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_series")
public class BookSeriesVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherVO publisher;

    @OneToMany(mappedBy = "bookSeries", cascade = CascadeType.ALL)
    private List<BookVO> bookList;

}
