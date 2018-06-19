package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddressDto {

    private String index ="";

    private String city ="";

    private String street ="";

    private String house ="";

    private String housing ="";

    private String building ="";

    private String room ="";

}
