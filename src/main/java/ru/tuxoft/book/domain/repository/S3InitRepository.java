package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.S3InitVO;

public interface S3InitRepository extends JpaRepository<S3InitVO, Long> {

}
