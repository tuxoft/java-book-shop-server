package ru.tuxoft.s3.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "s3init")
public class S3InitVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s3init_seq_gen")
    @SequenceGenerator(name = "s3init_seq_gen", sequenceName = "s3init_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "init")
    private Boolean init;

}
