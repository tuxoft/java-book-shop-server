package ru.tuxoft.book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.dto.BookDto;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryVO,Long> {

    List<CategoryVO> findByIdIn(List<Long> categoryList);

    List<CategoryVO> findByParentId(Long parentId);
}
