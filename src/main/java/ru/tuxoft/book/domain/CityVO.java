package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "cityVO", cascade = CascadeType.ALL)
    private List<BookVO> bookVOList;

}
