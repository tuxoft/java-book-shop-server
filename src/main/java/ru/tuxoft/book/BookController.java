package ru.tuxoft.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getBookById(
            @PathVariable("id") Long id
    ) {
        try {
            return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories")
    public List<CategoryDto> getCategoryList() {
        return bookService.getCategoryList();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories/{id}/books")
    public ResponseEntity getBookByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        try {
            return new ResponseEntity<>(bookService.getBookByCategory(id, start, count, sort, order),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity getAuthorsByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        try {
            return new ResponseEntity<>(bookService.getBookByAuthor(id, start, count, sort, order),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
