package ru.tuxoft.book.dto;

import lombok.Data;
import ru.tuxoft.book.domain.BookVO;

import java.math.BigDecimal;
import java.util.Date;

@Data
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

    /*private PublisherDto publisher;

    private List<BookAuthorDto> authors;

    private CityDto city;

    private LanguageDto language;

    private BookSeriesDto bookSeries;

    private List<CategoryDto> categories;
*/
    public BookDto(BookVO bookVO) {

    }

}
