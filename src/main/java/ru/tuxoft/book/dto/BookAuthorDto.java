package ru.tuxoft.book.dto;

import lombok.Data;

@Data
public class BookAuthorDto {

    private Long id;

    private AuthorDto author;

    private Integer position;

}
