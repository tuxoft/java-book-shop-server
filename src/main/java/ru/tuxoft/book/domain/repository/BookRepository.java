package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.BookVO;

public interface BookRepository extends JpaRepository<BookVO, Long> {

}
