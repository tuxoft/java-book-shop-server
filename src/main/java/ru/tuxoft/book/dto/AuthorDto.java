package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.AuthorVO;

@Data
@NoArgsConstructor
public class AuthorDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

}
