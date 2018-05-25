package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.CityDto;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class CityVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "code_city")
    private String codeCity;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<BookVO> bookList;

}
