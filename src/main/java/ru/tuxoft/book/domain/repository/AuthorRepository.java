package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.AuthorVO;

import java.util.LongSummaryStatistics;

/**
 * Created by Valera on 27.04.2018.
 */
public interface AuthorRepository extends JpaRepository<AuthorVO, Long> {

}
