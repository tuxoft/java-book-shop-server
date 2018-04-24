package ru.tuxoft.book.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "languages")
public class LanguageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "code_language")
    private String codeLanguage;

    @OneToMany(mappedBy = "languageVO", cascade = CascadeType.ALL)
    private List<BookVO> bookVOList;

}
