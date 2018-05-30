package ru.tuxoft.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tuxoft.admin.dto.DictionaryDto;
import ru.tuxoft.book.BookService;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.content.ContentService;
import ru.tuxoft.content.dto.MenuDto;
import ru.tuxoft.paging.ListResult;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Autowired
    ContentService contentService;

    @RequestMapping(method = RequestMethod.GET, path = "/menu")
    public MenuDto getMenu() throws IOException {
        return contentService.getAdminMenu();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/books/{id}")
    public BookDto getBook(
            @PathVariable("id") Long bookId
    ) {
        return bookService.getBookById(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/books", produces = "application/json")
    public BookDto changeBook(
            @RequestBody BookDto bookDto
    ) {
        return adminService.updateBook(bookDto);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dictionary")
    public ListResult<DictionaryDto> getDictionary(
            @RequestParam(name = "dictionary") String dictionary,
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return adminService.getDictionary(dictionary, parentId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/dictionary/search")
    public ListResult<DictionaryDto> searchDictionary(
            @RequestParam(name = "dictionary") String dictionary,
            @RequestParam(name = "query") String query,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return adminService.searchDictionary(dictionary, query, parentId, start, pageSize);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file")
    public ResponseEntity uploadPicture(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(adminService.uploadFile(file), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/books")
    public ListResult<BookDto> getBookList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        return bookService.getBookList(start, pageSize, sort, order);
    }


}
