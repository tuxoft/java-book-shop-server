package ru.tuxoft.content.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.s3.domain.FileVO;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "promo_pictures")
public class PromoPictureVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "picture_file_id")
    private FileVO picture;

}
