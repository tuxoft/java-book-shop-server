package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface CategoryMapper {

    CategoryDto categoryVOToCategoryDto(CategoryVO vo);

    CategoryVO categoryDtoToCategoryVO(CategoryDto dto);

}
