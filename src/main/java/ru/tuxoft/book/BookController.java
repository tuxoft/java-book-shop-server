package ru.tuxoft.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;

import java.util.List;

@RestController
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(method = RequestMethod.GET, path = "/books")
    public List<BookDto> getBookList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
            ) {
        return bookService.getBookList(start, count, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/books/{id}")
    public BookDto getBookById(
            @PathVariable("id") Long id
    ) {
        return bookService.getBookById(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories")
    public List<CategoryDto> getCategoryList() {
        return bookService.getCategoryList();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories/{id}/books")
    public List<BookDto> getBookByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        return bookService.getBookByCategory(id, start, count, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/authors")
    public List<AuthorDto> getAuthorsList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        return bookService.getAuthorList(start, count, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/authors/{id}/books")
    public List<BookDto> getAuthorsByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        return bookService.getBookByAuthor(id, start, count, sort, order);
    }

}
