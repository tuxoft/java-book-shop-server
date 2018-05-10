package ru.tuxoft.book.dto;

import lombok.Data;
import ru.tuxoft.book.domain.CategoryVO;

import java.util.List;

@Data
public class CategoryDto {

    private Long id;

    private String name;

    private Long parentId;

    private List<BookDto> bookList;

    public CategoryDto(CategoryVO categoryVO) {

        this.id = categoryVO.getId();

        this.name = categoryVO.getName();

        this.parentId = categoryVO.getParentId();

    }
}
