package ru.tuxoft.book.dto;

import ru.tuxoft.book.domain.CategoryVO;

import java.util.List;

public class CategoryDto {

    private Long id;

    private String name;

    private String codeCategory;

    private List<BookDto> bookList;

    public CategoryDto(CategoryVO categoryVO) {

        this.id = categoryVO.getId();

        this.name = categoryVO.getName();

        this.codeCategory = categoryVO.getCodeCategory();

    }
}
