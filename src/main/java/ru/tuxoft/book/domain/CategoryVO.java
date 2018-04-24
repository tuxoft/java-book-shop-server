package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "code_category")
    private String codeCategory;

    @OneToMany(mappedBy = "categoryVO", cascade = CascadeType.ALL)
    private List<BookVO> bookVOList;


}
