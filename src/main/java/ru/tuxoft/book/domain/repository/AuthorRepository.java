package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.AuthorVO;

import java.util.LongSummaryStatistics;

public interface AuthorRepository extends JpaRepository<AuthorVO, Long> {

}
