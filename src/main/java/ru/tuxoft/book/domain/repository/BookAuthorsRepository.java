package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.tuxoft.book.domain.BookAuthorsVO;

public interface BookAuthorsRepository extends JpaRepository<BookAuthorsVO, Long> {

    @Query("select ba from BookAuthorsVO ba join ba.author a join ba.book b where b.id = :bookId and a.id = :authorId ")
    BookAuthorsVO findByBookIdAndAuthorId(@Param("bookId") Long bookId,@Param("authorId") Long authorId);

    @Modifying
    @Transactional
    @Query(value = "delete from book_authors ba where ba.book_id = :bookId", nativeQuery = true)
    void deleteByBookId(@Param("bookId") Long id);
}
