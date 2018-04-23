package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.BookVO;

/**
 * Created by Valera on 23.04.2018.
 */
public interface BookRepository extends JpaRepository<BookVO, Long> {

}
