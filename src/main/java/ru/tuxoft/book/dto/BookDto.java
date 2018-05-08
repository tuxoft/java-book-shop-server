package ru.tuxoft.book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.tuxoft.book.domain.BookAuthorsVO;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String subtitle;

    private Integer edition;

    private String isbn;

    private String udc;

    private String bbk;

    private Integer publicationYear;

    private Integer circulation;

    private BigDecimal price;

    private Integer inStock;

    private Integer weight;

    private String ageLimit;

    private String format;

    private String coverType;

    private Integer pageCount;

    private String coverUrl;

    private List<BookAuthorDto> authors;

    private PublisherDto publisher;

    private String city;

    private List<CategoryDto> categories;

    private BookSeriesDto bookSeries;

    private String language;

    public BookDto(BookVO bookVO) {
        this.id = bookVO.getId();

        this.title = bookVO.getTitle();

        this.subtitle = bookVO.getSubtitle();

        this.edition = bookVO.getEdition();

        this.isbn = bookVO.getIsbn();

        this.udc = bookVO.getUdc();

        this.bbk = bookVO.getBbk();

        this.publicationYear = bookVO.getPublicationYear();

        this.circulation = bookVO.getCirculation();

        this.price = bookVO.getPrice();

        this.inStock = bookVO.getInStock();

        this.weight = bookVO.getWeight();

        this.ageLimit = bookVO.getAgeLimit();

        this.format = bookVO.getFormat();

        this.coverType = bookVO.getCoverType();

        this.pageCount = bookVO.getPageCount();

        this.authors = new ArrayList<>();
        for (BookAuthorsVO authorVO: bookVO.getBookAuthorsVOList()) {
            this.authors.add(new BookAuthorDto(authorVO));
        }

        this.publisher = new PublisherDto(bookVO.getPublisherVO());

        if (bookVO.getCityVO() != null) {
            this.city = bookVO.getCityVO().getName();
        }

        this.categories = new ArrayList<>();
        for (CategoryVO categoryVO: bookVO.getCategoryVOList()) {
            this.categories.add(new CategoryDto(categoryVO));
        }

        this.bookSeries = new BookSeriesDto(bookVO.getBookSeriesVO());

        if (bookVO.getLanguageVO() != null) {
            this.language = bookVO.getLanguageVO().getName();
        }

        if (bookVO.getCoverFile() != null) {
            this.coverUrl = "/api/file/s3/" + bookVO.getCoverFile().getBucket() + "/" + bookVO.getCoverFile().getKey() + "." + bookVO.getCoverFile().getName().substring(bookVO.getCoverFile().getName().lastIndexOf(".") + 1);
        }


    }

}
