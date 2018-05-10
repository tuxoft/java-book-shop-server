package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookAuthorsVO;

@Data
@NoArgsConstructor
public class BookAuthorDto {

    private AuthorDto author;

    private Integer position;

    public BookAuthorDto(BookAuthorsVO authorVO) {
        this.author = new AuthorDto(authorVO.getAuthor());
        this.position = authorVO.getPosition();
    }
}
