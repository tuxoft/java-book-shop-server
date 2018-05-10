package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.PublisherVO;

import java.util.List;

@Data
@NoArgsConstructor
public class PublisherDto {

    private Long id;

    private String name;

    private List<BookDto> bookList;

    private List<BookSeriesDto> bookSeriesList;

    public PublisherDto(PublisherVO publisherVO) {

        this.id = publisherVO.getId();

        this.name = publisherVO.getName();

    }
}
