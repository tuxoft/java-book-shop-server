package ru.tuxoft.profile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.domain.ShopCityVO;
import ru.tuxoft.order.dto.AddressDto;
import ru.tuxoft.order.dto.ShopCityDto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class ProfileDto {

    private Long id;

    private String userId;

    private ShopCityDto shopCity;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String sex;

    private String birthdate;

    private AddressDto addr;

}
