package ru.tuxoft.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.dto.PublisherDto;

@Data
@NoArgsConstructor
public class BookSeriesEditDto {

    private Long id;

    private String name;

    private PublisherDto publisher;

}
