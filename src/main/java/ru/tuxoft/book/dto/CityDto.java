package ru.tuxoft.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityDto {

    private Long id;

    private String name;

    private String codeCity;

    private List<BookDto> bookList;

}
