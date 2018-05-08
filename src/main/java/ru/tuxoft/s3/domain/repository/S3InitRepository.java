package ru.tuxoft.s3.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.s3.domain.S3InitVO;

public interface S3InitRepository extends JpaRepository<S3InitVO, Long> {

}
