package ru.tuxoft.book.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tuxoft.admin.dto.BookSeriesEditDto;
import ru.tuxoft.admin.dto.CategoryEditDto;
import ru.tuxoft.book.domain.*;
import ru.tuxoft.book.domain.repository.AuthorRepository;
import ru.tuxoft.book.domain.repository.CategoryRepository;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookAuthorsDto;
import ru.tuxoft.s3.domain.FileVO;
import ru.tuxoft.s3.domain.repository.FileRepository;

import java.util.Optional;

@Component
public class BookMapperResolver {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookMapper bookMapper;

    @ObjectFactory
    public FileVO resolve(String coverUrl, @TargetType Class<FileVO> type) {
        String key = coverUrl.substring(coverUrl.lastIndexOf("/") + 1,coverUrl.lastIndexOf("."));
        return fileRepository.findByKey(key);
    }

    @ObjectFactory
    public AuthorVO resolve(AuthorDto authorDto, @TargetType Class<AuthorVO> type) {
        Optional<AuthorVO> authorOptional = authorRepository.findById(authorDto.getId());
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        } else {
            return null;
        }
    }

    @ObjectFactory
    public CategoryEditDto resolve(CategoryVO categoryVO, @TargetType Class<CategoryEditDto> type) {
        CategoryEditDto categoryEditDto = new CategoryEditDto();
        categoryEditDto.setId(categoryVO.getId());
        categoryEditDto.setName(categoryVO.getName());
        if (categoryVO.getParentId() != null) {
            Optional<CategoryVO> parentCategoryOptional = categoryRepository.findById(categoryVO.getParentId());
            if (parentCategoryOptional.isPresent()) {
                CategoryEditDto parentCategoryEditDto = new CategoryEditDto();
                parentCategoryEditDto.setId(parentCategoryOptional.get().getId());
                parentCategoryEditDto.setName(parentCategoryOptional.get().getName());
                categoryEditDto.setParent(parentCategoryEditDto);
            }
        }
        return categoryEditDto;
    }

}
