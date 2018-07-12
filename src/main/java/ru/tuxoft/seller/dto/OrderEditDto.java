package ru.tuxoft.seller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.order.dto.OrderDto;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderEditDto extends OrderDto {

    private String workStatus;

    private String workStatusDescription;

    private SellerDto worker;

    private List<ActionDto> changeHistory;

}
