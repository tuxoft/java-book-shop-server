package ru.tuxoft.s3.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookVO;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.*;
import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "files")
public class FileVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bucket")
    private String bucket;

    @Column(name = "key")
    private String key;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "coverFile", cascade = CascadeType.ALL)
    private List<BookVO> bookVOList;

    public FileVO(File f) {
        this.name = f.getName();
        this.mimeType = new MimetypesFileTypeMap().getContentType(f);
        this.fileSize = f.length();
    }
}
