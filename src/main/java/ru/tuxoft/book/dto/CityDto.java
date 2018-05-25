package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.CityVO;

import java.util.List;

@Data
@NoArgsConstructor
public class CityDto {

    private Long id;

    private String name;

    private String codeCity;

    private List<BookDto> bookList;

}
