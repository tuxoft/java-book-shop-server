package ru.tuxoft.book;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.domain.repository.AuthorRepository;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.book.domain.repository.CategoryRepository;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;
import ru.tuxoft.book.mapper.AuthorMapper;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.book.mapper.CategoryMapper;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    AuthorMapper authorMapper;

    public ListResult<BookDto> getBookList(int start, int pageSize, String sort, String order) {
        int page = start / pageSize;
        ListResult<BookDto> result = new ListResult<>(new Meta((int) bookRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        List<BookDto> data = bookRepository.findAll(PageRequest.of(page, pageSize)).stream().map(e -> bookMapper.bookVOToBookDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public BookDto getBookById(Long id) throws IllegalArgumentException {
        Optional<BookVO> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            return bookMapper.bookVOToBookDto(bookOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Книги с указанным id в БД не обнаружено");
        }
    }

    public List<CategoryDto> getCategoryList() {
        return categoryRepository.findAll().stream().map(e -> categoryMapper.categoryVOToCategoryDto(e)).collect(Collectors.toList());
    }


    public ListResult<BookDto> getBookByCategory(Long id, int start, int pageSize, String sort, String order) throws IllegalArgumentException {
        Optional<CategoryVO> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            int page = start / pageSize;
            List<Long> idList = new ArrayList<>();
            idList.add(id);
            idList.addAll(getAllChildrenByCategoryId(id));
            List<BookVO> bookList = bookRepository.findBookByCategoryIdIn(idList, PageRequest.of(page, pageSize));
            ListResult<BookDto> result = new ListResult<>(new Meta(bookRepository.countBookByCategoryIdIn(idList).intValue(), new Paging(start, pageSize)), new ArrayList<>());
            result.setData(bookList.stream().map(e -> bookMapper.bookVOToBookDto(e)).collect(Collectors.toList()));
            return result;
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Категории с указанным id в БД не обнаружено");
        }
    }

    private List<Long> getAllChildrenByCategoryId(Long id) {
        List<Long> childrenIds = categoryRepository.findIdByParentId(id);
        if (childrenIds != null && childrenIds.size() > 0) {
            List<Long> childrenChildrenIds = new ArrayList<>();
            for (Long childrenId: childrenIds) {
                childrenChildrenIds.addAll(getAllChildrenByCategoryId(childrenId));
            }
            childrenIds.addAll(childrenChildrenIds);
        }
        return childrenIds;
    }

    public List<AuthorDto> getAuthorList(int start, int count, String sort, String order) {
        return authorRepository.findAll(PageRequest.of(start, count)).stream().map(e -> authorMapper.toDto(e)).collect(Collectors.toList());
    }

    public ListResult<BookDto> getBookByAuthor(Long id, int start, int pageSize, String sort, String order) throws IllegalArgumentException {
        Optional<AuthorVO> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            int page = start / pageSize;
            List <BookDto> bookList = authorOptional.get().getBookAuthorsList().stream().map(e -> bookMapper.bookVOToBookDto(e.getBook())).collect(Collectors.toList());
            ListResult<BookDto> bookListResult = new ListResult<>(new Meta(bookList.size(), new Paging(start, pageSize)), new ArrayList<>());
            bookListResult.setData(bookList.subList(start, start + pageSize));
            return bookListResult;

        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Автора с указанным id в БД не обнаружено");
        }
    }
}
