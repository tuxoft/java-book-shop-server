package ru.tuxoft.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.s3.domain.FileVO;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pickup_points")
public class PickupPointVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "coord_x")
    private Double coordX;

    @Column(name = "coord_y")
    private Double coordY;

    @Column(name = "name")
    private String name;

    @Column(name = "shop_city_id")
    private Long shopCityId;

    @Column(name = "work_period")
    private String workPeriod;

    @ManyToOne
    @JoinColumn(name = "icon_file_id")
    private FileVO iconFile;

    @Column(name = "addr_index")
    private String addrIndex;

    @Column(name = "addr_city")
    private String addrCity;

    @Column(name = "addr_street")
    private String addrStreet;

    @Column(name = "addr_house")
    private String addrHouse;

    @Column(name = "addr_housing")
    private String addrHousing;

    @Column(name = "addr_building")
    private String addrBuilding;

    @Column(name = "addr_room")
    private String addrRoom;

    @Column(name = "pay_case")
    private String payCase;

    @Column(name = "send_price")
    private BigDecimal sendPrice;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
