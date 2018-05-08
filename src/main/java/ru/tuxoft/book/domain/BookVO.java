package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.s3.domain.FileVO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "edition")
    private Integer edition;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "udc")
    private String udc;

    @Column(name = "bbk")
    private String bbk;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "circulation")
    private Integer circulation;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "in_stock")
    private Integer inStock;

    @ManyToOne
    @JoinColumn(name = "cover_file_id")
    private FileVO coverFile;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookAuthorsVO> bookAuthorsVOList;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherVO publisherVO;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityVO cityVO;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryVO> categoryVOList;

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeriesVO bookSeriesVO;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private LanguageVO languageVO;

}

