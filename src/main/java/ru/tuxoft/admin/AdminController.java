package ru.tuxoft.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tuxoft.admin.dto.*;
import ru.tuxoft.book.BookService;
import ru.tuxoft.book.dto.*;
import ru.tuxoft.content.ContentService;
import ru.tuxoft.content.dto.MenuDto;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.search.SearchService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Autowired
    SearchService searchService;

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

    @RequestMapping(method = RequestMethod.DELETE, path = "/books/{id}")
    public void deleteBook(
            @PathVariable("id") Long bookId
    ) {
        adminService.deleteBookById(bookId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/books")
    public ListResult<BookDto> getBookList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchBook(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/authors/{id}")
    public AuthorDto getAuthor(
            @PathVariable("id") Long authorId
    ) {
        return bookService.getAuthorById(authorId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/authors", produces = "application/json")
    public AuthorDto changeAuthor(
            @RequestBody AuthorDto authorDto
    ) {
        return adminService.updateAuthor(authorDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/authors/{id}")
    public void deleteAuthor(
            @PathVariable("id") Long authorId
    ) {
        adminService.deleteAuthorById(authorId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/authors")
    public ListResult<AuthorDto> getAuthorList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchAuthor(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publishers/{id}")
    public PublisherDto getPublisher(
            @PathVariable("id") Long publisherId
    ) {
        return bookService.getPublisherById(publisherId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/publishers", produces = "application/json")
    public PublisherDto changePublisher(
            @RequestBody PublisherDto publisherDto
    ) {
        return adminService.updatePublisher(publisherDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/publishers/{id}")
    public void deletePublisher(
            @PathVariable("id") Long publisherId
    ) {
        adminService.deletePublisherById(publisherId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/publishers")
    public ListResult<PublisherDto> getPublisherList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchPublisher(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories/{id}")
    public CategoryEditDto getCategory(
            @PathVariable("id") Long categoryId
    ) {
        return adminService.getCategoryEditById(categoryId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/categories", produces = "application/json")
    public CategoryEditDto changeCategory(
            @RequestBody CategoryEditDto categoryDto
    ) {
        return adminService.updateCategory(categoryDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/categories/{id}")
    public void deleteCategory(
            @PathVariable("id") Long categoryId
    ) {
        adminService.deleteCategoryById(categoryId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categories")
    public ListResult<CategoryEditDto> getCategoryList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchCategory(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookSeries/{id}")
    public BookSeriesEditDto getBookSeries(
            @PathVariable("id") Long bookSeriesId
    ) {
        return adminService.getBookSeriesEditById(bookSeriesId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bookSeries", produces = "application/json")
    public BookSeriesEditDto changeBookSeries(
            @RequestBody BookSeriesEditDto bookSeriesDto
    ) {
        return adminService.updateBookSeries(bookSeriesDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/bookSeries/{id}")
    public void deleteBookSeries(
            @PathVariable("id") Long bookSeriesId
    ) {
        adminService.deleteBookSeriesById(bookSeriesId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookSeries")
    public ListResult<BookSeriesEditDto> getBookSeriesList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchBookSeries(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cities/{id}")
    public CityDto getCity(
            @PathVariable("id") Long cityId
    ) {
        return bookService.getCityById(cityId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cities", produces = "application/json")
    public CityDto changeCity(
            @RequestBody CityDto cityDto
    ) {
        return adminService.updateCity(cityDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cities/{id}")
    public void deleteCity(
            @PathVariable("id") Long cityId
    ) {
        adminService.deleteCityById(cityId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cities")
    public ListResult<CityDto> getCityList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchCity(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/languages/{id}")
    public LanguageDto getLanguage(
            @PathVariable("id") Long languageId
    ) {
        return bookService.getLanguageById(languageId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/languages", produces = "application/json")
    public LanguageDto changeLanguage(
            @RequestBody LanguageDto languageDto
    ) {
        return adminService.updateLanguage(languageDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/languages/{id}")
    public void deleteLanguage(
            @PathVariable("id") Long languageId
    ) {
        adminService.deleteLanguageById(languageId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/languages")
    public ListResult<LanguageDto> getLanguageList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchLanguage(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/promoPictures/{id}")
    public PromoPictureEditDto getPromoPicture(
            @PathVariable("id") Long promoPictureId
    ) {
        return adminService.getPromoPictureById(promoPictureId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/promoPictures", produces = "application/json")
    public PromoPictureEditDto changePromoPicture(
            @RequestBody PromoPictureEditDto promoPictureDto
    ) {
        return adminService.updatePromoPicture(promoPictureDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/promoPictures/{id}")
    public void deletePromoPicture(
            @PathVariable("id") Long promoPictureId
    ) {
        adminService.deletePromoPictureById(promoPictureId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/promoPictures")
    public ListResult<PromoPictureEditDto> getPromoPictureList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchPromoPicture(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categoryCarousels/{id}")
    public CategoryCarouselEditDto getCategoryCarousels(
            @PathVariable("id") Long categoryCarouselId
    ) {
        return adminService.getCategoryCarouselById(categoryCarouselId);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/categoryCarousels", produces = "application/json")
    public CategoryCarouselEditDto changeCategoryCarousel(
            @RequestBody CategoryCarouselEditDto categoryCarouselDto
    ) {
        return adminService.updateCategoryCarousel(categoryCarouselDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/categoryCarousels/{id}")
    public void deleteCategoryCarousel(
            @PathVariable("id") Long categoryCarouselId
    ) {
        adminService.deleteCategoryCarouselById(categoryCarouselId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categoryCarousels")
    public ListResult<CategoryCarouselEditDto> getCategoryCarouselList(
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "order", defaultValue = "ASC") String order
    ) {
        return searchService.searchCategoryCarousel(query, start, pageSize, sort, order);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file")
    public ResponseEntity uploadPicture(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(adminService.uploadFile(file), HttpStatus.OK);
    }

}
