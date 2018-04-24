package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private PublisherVO publisherVO;

    @OneToMany(mappedBy = "bookSeriesVO", cascade = CascadeType.ALL)
    private List<BookVO> bookVOList;

}
