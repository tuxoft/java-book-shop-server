package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.BookVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String subtitle;

    private Integer edition;

    private String ISBN;

    private String UDC;

    private String BBK;

    private String cutterCode;

    private Date publicationYear;

    private Integer circulation;

    private BigDecimal price;

    private Integer inStock;

    private String coverUrl;

    private List<BookAuthorDto> authors;

    private PublisherDto publisher;

    private CityDto city;

    private List<CategoryDto> categories;

    private BookSeriesDto bookSeries;

    private LanguageDto language;

    public BookDto(BookVO bookVO) {

    }

}
