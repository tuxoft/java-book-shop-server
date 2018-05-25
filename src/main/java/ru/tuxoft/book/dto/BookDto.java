package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String description;

    private Integer edition;

    private String isbn;

    private String udc;

    private String bbk;

    private Integer publicationYear;

    private Integer circulation;

    private BigDecimal price;

    private Integer inStock;

    private Integer weight;

    private AgeLimitDto ageLimit;

    private String dimensions;

    private CoverTypeDto coverType;

    private Integer pageCount;

    private String coverUrl;

    private List<BookAuthorsDto> bookAuthors;

    private PublisherDto publisher;

    private CityDto city;

    private List<CategoryDto> categories;

    private BookSeriesDto bookSeries;

    private LanguageDto language;


}
