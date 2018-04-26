package ru.tuxoft.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.book.dto.BookDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<BookDto> getBookList(int start, int count, String sort, String order) {
        return bookRepository.findAll(PageRequest.of(start, count)).stream().map(e -> new BookDto(e)).collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Optional<BookVO> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            return new BookDto(bookOptional.get());
        } else {
            return null;
        }
    }
}
