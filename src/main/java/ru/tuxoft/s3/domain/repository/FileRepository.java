package ru.tuxoft.s3.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.s3.domain.FileVO;

public interface FileRepository extends JpaRepository<FileVO, Long>{

    FileVO findByKey(String key);
}
