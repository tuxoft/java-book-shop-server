package ru.tuxoft.book.dto;

import lombok.Data;
import ru.tuxoft.book.domain.BookAuthorsVO;

@Data
public class BookAuthorDto {

    private AuthorDto author;

    private Integer position;

    public BookAuthorDto(BookAuthorsVO authorVO) {
        this.author = new AuthorDto(authorVO.getAuthor());
        this.position = authorVO.getPosition();
    }
}
