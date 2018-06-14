package ru.tuxoft.content.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.CategoryVO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "category_carousels")
public class CategoryCarouselVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryVO category;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "deleted")
    private Boolean deleted = false;


}
