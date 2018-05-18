package ru.tuxoft.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.book.dto.BookDto;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public List<BookDto> getBookList(
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "count", defaultValue = "10") int count,
            @RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "order", defaultValue = "a") String order
    ) {
        return searchService.search(query, start, count, sort, order);
    }
}
