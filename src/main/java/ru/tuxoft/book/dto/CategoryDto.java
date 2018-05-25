package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.paging.ListResult;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private Long parentId;

    private List<BookDto> bookList;

}
