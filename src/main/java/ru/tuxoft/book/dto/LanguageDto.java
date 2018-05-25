package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.LanguageVO;

import java.util.List;

@Data
@NoArgsConstructor
public class LanguageDto {

    private Long id;

    private String name;

    private String codeLanguage;

    private List<BookDto> bookList;

}
