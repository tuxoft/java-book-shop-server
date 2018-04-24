package ru.tuxoft.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublisherDto {

    private Long id;

    private String name;

    private List<BookDto> bookList;

    private List<BookSeriesDto> bookSeriesList;
}
