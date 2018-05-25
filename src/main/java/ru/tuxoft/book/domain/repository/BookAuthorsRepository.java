package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tuxoft.book.domain.BookAuthorsVO;

public interface BookAuthorsRepository extends JpaRepository<BookAuthorsVO, Long> {

    @Query("select ba from BookAuthorsVO ba join ba.author join ba.book where ba.book")
    BookAuthorsVO findByBookIdAndAuthorId(Long bookId, Long authorId);
}
