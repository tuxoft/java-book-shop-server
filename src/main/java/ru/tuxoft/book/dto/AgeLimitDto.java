package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.AgeLimitVO;

import java.util.List;

@Data
@NoArgsConstructor
public class AgeLimitDto {

    private Long id;

    private String name;

    private List<BookDto> bookList;

}
