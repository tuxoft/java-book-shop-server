package ru.tuxoft.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddressDto {

    private String city ="";

    private String index ="";

    private String housing ="";

    private String street ="";

    private String house ="";

    private String room ="";

    private String building ="";

}
