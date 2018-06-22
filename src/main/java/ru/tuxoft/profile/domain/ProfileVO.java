package ru.tuxoft.profile.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class ProfileVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId = "";

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "first_name")
    private String firstName = "";

    @Column(name = "middle_name")
    private String middleName = "";

    @Column(name = "last_name")
    private String lastName = "";

    @Column(name = "email")
    private String email = "";

    @Column(name = "phone_number")
    private String phoneNumber = "";

    @Column(name = "sex")
    private String sex = "";

    @Column(name = "birthdate")
    private String birthdate = "";

    @Column(name = "addr_index")
    private String addrIndex = "";

    @Column(name = "addr_city")
    private String addrCity = "";

    @Column(name = "addr_street")
    private String addrStreet = "";

    @Column(name = "addr_house")
    private String addrHouse = "";

    @Column(name = "addr_housing")
    private String addrHousing = "";

    @Column(name = "addr_building")
    private String addrBuilding = "";

    @Column(name = "addr_room")
    private String addrRoom = "";

}
