package ru.tuxoft.book.dto;

import lombok.Data;

@Data
public class AuthorDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;
}
