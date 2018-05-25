package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.CoverTypeDto;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cover_types")
public class CoverTypeVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "coverType", cascade = CascadeType.ALL)
    private List<BookVO> bookList;

}
