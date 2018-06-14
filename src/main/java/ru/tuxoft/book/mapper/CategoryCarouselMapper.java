package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.admin.dto.CategoryCarouselEditDto;
import ru.tuxoft.content.domain.CategoryCarouselVO;

@Mapper(componentModel = "spring", uses = {BookMapperResolver.class})
public interface CategoryCarouselMapper {

    CategoryCarouselEditDto categoryCarouselVOToCategoryCarouselEditDto(CategoryCarouselVO categoryCarouselVO);

    @Mappings({
            @Mapping(target = "deleted", ignore = true),
    })
    CategoryCarouselVO categoryCarouselEditDtoToCategoryCarouselVO(CategoryCarouselEditDto categoryCarouselDto);

}
