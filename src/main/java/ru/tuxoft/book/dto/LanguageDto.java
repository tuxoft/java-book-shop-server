package ru.tuxoft.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class LanguageDto {

    private Long id;

    private String name;

    private String codeLanguage;

    private List<BookDto> bookList;

}
