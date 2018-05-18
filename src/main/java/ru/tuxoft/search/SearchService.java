package ru.tuxoft.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.book.dto.BookDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
@Slf4j
public class SearchService {

    @Autowired
    DataSource ds;

    @Autowired
    BookRepository bookRepository;

    public List<BookDto> search(String query, int start, int count, String sort, String order) {
        List<BookDto> result = new ArrayList<>();

        StringBuilder tsquery = new StringBuilder();

        query = query.replace('-', ' ').replaceAll("\\s+", " ").trim().toLowerCase().replace('ё','е').replace('й','и');

        StringTokenizer st = new StringTokenizer(query, " ");

        while (st.hasMoreTokens()) {
            tsquery.append(st.nextToken()).append(":*");
            if (st.hasMoreTokens()) {
                tsquery.append(" & ");
            }
        }

        StringBuilder searchField = new StringBuilder("");

        StringBuilder tsvector = new StringBuilder(" setweight(to_tsvector('russian', coalesce(title,'')), 'A') || setweight(to_tsvector('russian', coalesce(book_series.name,'')), 'C')  || setweight(to_tsvector('russian', coalesce(last_name,'')), 'B') || setweight(to_tsvector('russian', coalesce(description,'')), 'D') ");

        StringBuilder fields = new StringBuilder("select b.id, ts_rank(");
        fields.append(tsvector).append(", q) AS rank ");

        StringBuilder from = new StringBuilder(" from books b left join book_series on b.book_series_id = book_series.id inner join book_authors on b.id = book_authors.book_id left join authors on authors.id = book_authors.author_id ");
        from.append(", to_tsquery('russian',?) q");

        StringBuilder where = new StringBuilder(" where ");
        where.append(tsvector).append(" @@ q ");

        StringBuilder orderBy = new StringBuilder(" order by rank DESC");

        try (Connection conn = ds.getConnection()) {

            String select = fields.toString() + from.toString() + where.toString() + orderBy.toString();

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

                        while (rs.next() && i++ < count) {
                            Long id = rs.getLong("id");
                            Optional<BookVO> book = bookRepository.findById(id);
                            if (book.isPresent()) {
                                BookDto dto = new BookDto(book.get());
                                result.add(dto);
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
}
