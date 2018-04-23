package ru.tuxoft.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.book.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<BookDto> getBookList(int start, int count, String sort, String order) {
        List<BookDto> result = bookRepository.findAll().stream().map(e -> new BookDto(e));
    }

}
