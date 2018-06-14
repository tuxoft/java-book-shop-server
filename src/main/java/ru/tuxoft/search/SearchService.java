package ru.tuxoft.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tuxoft.admin.AdminService;
import ru.tuxoft.admin.dto.BookSeriesEditDto;
import ru.tuxoft.admin.dto.CategoryCarouselEditDto;
import ru.tuxoft.admin.dto.CategoryEditDto;
import ru.tuxoft.admin.dto.PromoPictureEditDto;
import ru.tuxoft.book.BookService;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.repository.*;
import ru.tuxoft.book.dto.*;
import ru.tuxoft.book.mapper.*;
import ru.tuxoft.content.domain.repository.CategoryCarouselRepository;
import ru.tuxoft.content.domain.repository.PromoPictureRepository;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    @Autowired
    DataSource ds;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookSeriesRepository bookSeriesRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    BookService bookService;

    @Autowired
    AdminService adminService;

    @Autowired
    PromoPictureRepository promoPictureRepository;

    @Autowired
    PromoPictureMapper promoPictureMapper;

    @Autowired
    CategoryCarouselRepository categoryCarouselRepository;

    @Autowired
    CategoryCarouselMapper categoryCarouselMapper;

    public ListResult<BookDto> searchBook(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return bookService.getBookList(start, pageSize, sort, order);
        }

        ListResult<BookDto> result = new ListResult<>(new Meta(-1, new Paging(start, pageSize)), new ArrayList<>());

        StringBuilder tsquery = new StringBuilder();

        sort = sort.replaceAll("([a-z])([A-Z]+)", "$1_$2");

        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');

        StringTokenizer st = new StringTokenizer(query, " ");

        while (st.hasMoreTokens()) {
            tsquery.append(st.nextToken()).append(":*");
            if (st.hasMoreTokens()) {
                tsquery.append(" & ");
            }
        }

        StringBuilder searchField = new StringBuilder("");

        StringBuilder tsvector = new StringBuilder(" setweight(to_tsvector('russian', coalesce(title,'')), 'A') || setweight(to_tsvector('russian', coalesce(book_series.name,'')), 'C')  || setweight(to_tsvector('russian', coalesce(last_name,'')), 'B') || setweight(to_tsvector('russian', coalesce(description,'')), 'D') ");

        StringBuilder fields = new StringBuilder("select b.id, max(ts_rank(");
        fields.append(tsvector).append(", q)) AS rank ");

        if (!sort.equals("id")) {
            fields.append(", " + sort + " ");
        }

        StringBuilder from = new StringBuilder(" from books b left join book_series on b.book_series_id = book_series.id inner join book_authors on b.id = book_authors.book_id left join authors on authors.id = book_authors.author_id ");
        from.append(", to_tsquery('russian',?) q");

        StringBuilder where = new StringBuilder(" where ");
        where.append(tsvector).append(" @@ q ");

        StringBuilder orderBy = new StringBuilder(" order by rank DESC, " + sort + " " + order);

        StringBuilder groupBy = new StringBuilder(" group by b.id ");

        try (Connection conn = ds.getConnection()) {

            String selectCount = "select count(distinct b.id) " + from.toString() + where.toString();

            log.debug("Count SQL: {}", selectCount);

            try (PreparedStatement ps = conn.prepareStatement(selectCount)) {
                ps.setString(1, tsquery.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    result.getMeta().setTotal(rs.getInt(1));
                }
            }

            String select = fields.toString() + from.toString() + where.toString() + groupBy.toString() + orderBy.toString();

            log.debug("Select SQL: {}", select);

            try (PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ps.setString(1, tsquery.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    boolean hasMore = true;
                    if (start != 0) {
                        hasMore = rs.absolute(start);
                    }
                    if (hasMore) {

                        int i = 0;

                        while (rs.next() && i++ < pageSize) {
                            Long id = rs.getLong("id");
                            Optional<BookVO> book = bookRepository.findById(id);
                            if (book.isPresent()) {
                                BookDto dto = bookMapper.bookVOToBookDto(book.get());
                                result.getData().add(dto);
                                log.debug("Rank for {}: {}", dto.getTitle(), rs.getDouble("rank"));
                            }
                        }
                    }
                }

            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public ListResult<AuthorDto> searchAuthor(String query, int start, int pageSize, String sort, String order) {

        if (query.trim().isEmpty()) {
            return bookService.getAuthorList(start, pageSize, sort, order);
        }

        ListResult<AuthorDto> result = new ListResult<>(new Meta(-1, new Paging(start, pageSize)), new ArrayList<>());

        StringBuilder tsquery = new StringBuilder();

        sort = sort.replaceAll("([a-z])([A-Z]+)", "$1_$2");

        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');

        StringTokenizer st = new StringTokenizer(query, " ");

        while (st.hasMoreTokens()) {
            tsquery.append(st.nextToken()).append(":*");
            if (st.hasMoreTokens()) {
                tsquery.append(" & ");
            }
        }

        StringBuilder searchField = new StringBuilder("");

        StringBuilder tsvector = new StringBuilder(" setweight(to_tsvector('russian', coalesce(last_name,'')), 'A') || setweight(to_tsvector('russian', coalesce(first_name,'')), 'B')  || setweight(to_tsvector('russian', coalesce(middle_name,'')), 'C') || setweight(to_tsvector('russian', coalesce(books.title,'')), 'D') ");

        StringBuilder fields = new StringBuilder("select a.id, max(ts_rank(");
        fields.append(tsvector).append(", q)) AS rank ");

        if (!sort.equals("id")) {
            fields.append(", " + sort + " ");
        }

        StringBuilder from = new StringBuilder(" from authors a left join book_authors on a.id = book_authors.author_id left join books on books.id = book_authors.book_id ");
        from.append(", to_tsquery('russian',?) q");

        StringBuilder where = new StringBuilder(" where ");
        where.append(tsvector).append(" @@ q ");

        StringBuilder orderBy = new StringBuilder(" order by rank DESC, " + sort + " " + order);

        StringBuilder groupBy = new StringBuilder(" group by a.id ");

        try (Connection conn = ds.getConnection()) {

            String selectCount = "select count(distinct a.id) " + from.toString() + where.toString();

            log.debug("Count SQL: {}", selectCount);

            try (PreparedStatement ps = conn.prepareStatement(selectCount)) {
                ps.setString(1, tsquery.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    result.getMeta().setTotal(rs.getInt(1));
                }
            }

            String select = fields.toString() + from.toString() + where.toString() + groupBy.toString() + orderBy.toString();

            log.debug("Select SQL: {}", select);

            try (PreparedStatement ps = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ps.setString(1, tsquery.toString());
                try (ResultSet rs = ps.executeQuery()) {
                    boolean hasMore = true;
                    if (start != 0) {
                        hasMore = rs.absolute(start);
                    }
                    if (hasMore) {

                        int i = 0;

                        while (rs.next() && i++ < pageSize) {
                            Long id = rs.getLong("id");
                            Optional<AuthorVO> author = authorRepository.findById(id);
                            if (author.isPresent()) {
                                AuthorDto dto = authorMapper.toDto(author.get());
                                result.getData().add(dto);
                                log.debug("Rank for {}: {}", dto.getLastName(), rs.getDouble("rank"));
                            }
                        }
                    }
                }

            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return result;

    }

    public ListResult<PublisherDto> searchPublisher(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return bookService.getPublisherList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<PublisherDto> result = new ListResult<>(new Meta(publisherRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<PublisherDto> data = publisherRepository.findByNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.PublisherVOToPublisherDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<CategoryEditDto> searchCategory(String query, int start, int pageSize, String sort, String order) {
        if (sort.equals("parent.name")) {
            sort = "parentId";
        }
        if (query.trim().isEmpty()) {
            return adminService.getCategoryEditList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<CategoryEditDto> result = new ListResult<>(new Meta(categoryRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CategoryEditDto> data = categoryRepository.findByNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> categoryMapper.categoryVOToCategoryEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<BookSeriesEditDto> searchBookSeries(String query, int start, int pageSize, String sort, String order) {
        if (sort.equals("publisher.name")) {
            sort = "publisher";
        }
        if (query.trim().isEmpty()) {
            return adminService.getBookSeriesEditList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<BookSeriesEditDto> result = new ListResult<>(new Meta(bookSeriesRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<BookSeriesEditDto> data = bookSeriesRepository.findByNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.bookSeriesVOToBookSeriesEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<CityDto> searchCity(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return bookService.getCityList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<CityDto> result = new ListResult<>(new Meta(cityRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CityDto> data = cityRepository.findByNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.CityVOToCityDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<LanguageDto> searchLanguage(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return bookService.getLanguageList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<LanguageDto> result = new ListResult<>(new Meta(languageRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<LanguageDto> data = languageRepository.findByNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.LanguageVOToLanguageDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<PromoPictureEditDto> searchPromoPicture(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return adminService.getPromoPictureEditList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<PromoPictureEditDto> result = new ListResult<>(new Meta(promoPictureRepository.findCountByUrlLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<PromoPictureEditDto> data = promoPictureRepository.findByUrlLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> promoPictureMapper.promoPictureVOToPromoPictureEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<CategoryCarouselEditDto> searchCategoryCarousel(String query, int start, int pageSize, String sort, String order) {
        if (query.trim().isEmpty()) {
            return adminService.getCategoryCarouselEditList(start, pageSize, sort, order);
        }
        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё', 'е').replace('й', 'и');
        ListResult<CategoryCarouselEditDto> result = new ListResult<>(new Meta(categoryCarouselRepository.findCountByCategoryNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CategoryCarouselEditDto> data = categoryCarouselRepository.findByCategoryNameLike(query, PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> categoryCarouselMapper.categoryCarouselVOToCategoryCarouselEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }
}
