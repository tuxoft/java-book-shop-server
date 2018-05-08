package ru.tuxoft.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.domain.repository.AuthorRepository;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.book.domain.repository.CategoryRepository;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    public List<BookDto> getBookList(int start, int count, String sort, String order) {
        return bookRepository.findAll(PageRequest.of(start, count)).stream().map(e -> new BookDto(e)).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) throws IllegalArgumentException {
        Optional<BookVO> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            return new BookDto(bookOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Книги с указанным id в БД не обнаружено");
        }
    }

    public List<CategoryDto> getCategoryList() {
        return categoryRepository.findAll().stream().map(e -> new CategoryDto(e)).collect(Collectors.toList());
    }


    public List<BookDto> getBookByCategory(Long id, int start, int count, String sort, String order) throws IllegalArgumentException {
        Optional<CategoryVO> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get().getBookVOList().stream().map(e -> new BookDto(e)).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Категории с указанным id в БД не обнаружено");
        }

    }

    public List<AuthorDto> getAuthorList(int start, int count, String sort, String order) {
        return authorRepository.findAll(PageRequest.of(start, count)).stream().map(e -> new AuthorDto(e)).collect(Collectors.toList());
    }

    public List<BookDto> getBookByAuthor(Long id, int start, int count, String sort, String order) throws IllegalArgumentException {
        Optional<AuthorVO> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get().getBookAuthorsVOList().stream().map(e -> new BookDto(e.getBook())).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Автора с указанным id в БД не обнаружено");
        }
    }
}
