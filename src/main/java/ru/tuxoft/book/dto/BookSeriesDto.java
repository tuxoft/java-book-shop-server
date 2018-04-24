package ru.tuxoft.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookSeriesDto {

    private Long id;

    private String name;

    private PublisherDto publisher;

    private List<BookDto> bookList;

}
