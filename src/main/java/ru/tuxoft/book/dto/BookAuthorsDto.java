package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookAuthorsVO;

@Data
@NoArgsConstructor
public class BookAuthorsDto {

    private AuthorDto author;

    private Integer position;

}
