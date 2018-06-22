package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.s3.domain.FileVO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courier_services")
public class CourierServiceVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shop_city_id")
    private Long shopCityId;

    @ManyToOne
    @JoinColumn(name = "icon_file_id")
    private FileVO iconFile;

    @Column(name = "pay_case")
    private String payCase;

    @Column(name = "send_price")
    private BigDecimal sendPrice;

    @Column(name = "max_weight")
    private Integer maxWeight;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
