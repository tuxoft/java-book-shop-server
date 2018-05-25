package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.CoverTypeVO;

import java.util.List;

@Data
@NoArgsConstructor
public class CoverTypeDto {

    private Long id;

    private String name;

    private List<BookDto> bookList;

}
