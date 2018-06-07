package ru.tuxoft.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.paging.ListResult;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public ListResult<BookDto> getBookList(
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchBook(query, start, pageSize, sort, order);
    }
}
