package ru.tuxoft.book.dto;

import lombok.Data;
import ru.tuxoft.book.domain.BookSeriesVO;

import java.util.List;

@Data
public class BookSeriesDto {

    private Long id;

    private String name;

    private Long publisherId;

    private List<BookDto> bookList;

    public BookSeriesDto(BookSeriesVO bookSeriesVO) {

        this.id = bookSeriesVO.getId();

        this.name = bookSeriesVO.getName();

        this.publisherId = bookSeriesVO.getPublisherVO().getId();
    }
}
