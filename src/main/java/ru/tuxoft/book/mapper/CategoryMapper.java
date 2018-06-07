package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.admin.dto.CategoryEditDto;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;

@Mapper(componentModel = "spring", uses = {BookMapper.class, BookMapperResolver.class})
public interface CategoryMapper {

    CategoryDto categoryVOToCategoryDto(CategoryVO vo);

    CategoryVO categoryDtoToCategoryVO(CategoryDto dto);

    CategoryEditDto categoryVOToCategoryEditDto(CategoryVO vo);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "parentId", source = "parent.id")
    })
    CategoryVO categoryEditDtoToCategoryVO(CategoryEditDto dto);

}
