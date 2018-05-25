package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookSeriesVO;

import java.util.List;

@Data
@NoArgsConstructor
public class BookSeriesDto {

    private Long id;

    private String name;

    private Long publisherId;

    private List<BookDto> bookList;

}
