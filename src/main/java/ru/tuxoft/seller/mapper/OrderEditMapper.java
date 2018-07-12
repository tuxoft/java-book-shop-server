package ru.tuxoft.seller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.order.mapper.OrderMapper;
import ru.tuxoft.order.mapper.OrderMapperResolver;
import ru.tuxoft.order.mapper.ShopCityMapper;
import ru.tuxoft.seller.domain.ActionVO;
import ru.tuxoft.seller.domain.OrderEditVO;
import ru.tuxoft.seller.domain.SellerVO;
import ru.tuxoft.seller.dto.ActionDto;
import ru.tuxoft.seller.dto.OrderEditDto;
import ru.tuxoft.seller.dto.SellerDto;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, OrderEditMapperResolver.class, OrderMapperResolver.class, ShopCityMapper.class, BookMapper.class})
public interface OrderEditMapper {

    @Mappings({
            @Mapping(target = "paymentMethodText", ignore= true),
            @Mapping(target = "addr", ignore= true),
            @Mapping(target = "sendPrice", ignore= true),
            @Mapping(target = "sendOrgName", ignore= true),
            @Mapping(target = "workStatus", source = "orderWorkInfo.workStatus.value"),
            @Mapping(target = "workStatusDescription", source = "orderWorkInfo.workStatusDescription"),
            @Mapping(target = "worker", source = "orderWorkInfo.worker")
    })
    OrderEditDto orderEditVOToOrderEditDto(OrderEditVO vo);


    default SellerDto sellerVOToSellerDto(SellerVO vo){
        if ( vo == null ) {
            return null;
        }

        SellerDto sellerDto = new SellerDto();

        sellerDto.setId( vo.getUserId() );

        String name = vo.getLastName() + " ";

        if (vo.getFirstName() != null && !vo.getFirstName().trim().isEmpty()) {
            name = name + vo.getFirstName().substring(0,1) + ".";
        }
        if (vo.getMiddleName() != null && !vo.getMiddleName().trim().isEmpty()) {
            name = name + vo.getMiddleName().substring(0,1) + ".";
        }
        if (name.trim().isEmpty()) {
            sellerDto.setName(vo.getUserId());
        } else {
            sellerDto.setName(name.trim());
        }

        return sellerDto;
    }

}
