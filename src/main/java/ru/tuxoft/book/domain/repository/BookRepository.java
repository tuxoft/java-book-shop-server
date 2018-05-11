package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tuxoft.book.domain.BookVO;


import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<BookVO, Long> {

}
