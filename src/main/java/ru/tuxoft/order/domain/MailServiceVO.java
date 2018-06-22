package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.s3.domain.FileVO;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mail_services")
public class MailServiceVO {

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

    @Column(name = "send_price_cost")
    private BigDecimal sendPriceCost;

    @Column(name = "send_price_commission")
    private BigDecimal sendPriceCommission;

    @Column(name = "deleted")
    private Boolean deleted = false;


}
