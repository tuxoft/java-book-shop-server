package ru.tuxoft.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.book.dto.BookDto;

import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public List<BookDto> getBookList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
            ) {
        return bookService.getBookList(start, count, sort, order);
    }



}
