package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.FileVO;

public interface FileRepository extends JpaRepository<FileVO, Long>{

}
