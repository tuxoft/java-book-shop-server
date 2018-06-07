package ru.tuxoft.book;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.*;
import ru.tuxoft.book.domain.repository.*;
import ru.tuxoft.book.dto.*;
import ru.tuxoft.book.mapper.AuthorMapper;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.book.mapper.CategoryMapper;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    BookSeriesRepository bookSeriesRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    AuthorMapper authorMapper;

    public ListResult<BookDto> getBookList(int start, int pageSize, String sort, String order) {
        int page = start / pageSize;
        ListResult<BookDto> result = new ListResult<>(new Meta((int) bookRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        List<BookDto> data = bookRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.bookVOToBookDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public BookDto getBookById(Long id) throws IllegalArgumentException {
        Optional<BookVO> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            return bookMapper.bookVOToBookDto(bookOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Книги с указанным id в БД не обнаружено");
        }
    }

    public List<CategoryDto> getCategoryList() {
        return categoryRepository.findAll().stream().map(e -> categoryMapper.categoryVOToCategoryDto(e)).collect(Collectors.toList());
    }


    public ListResult<BookDto> getBookByCategory(Long id, int start, int pageSize, String sort, String order) throws IllegalArgumentException {
        Optional<CategoryVO> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            int page = start / pageSize;
            List<Long> idList = new ArrayList<>();
            idList.add(id);
            idList.addAll(getAllChildrenByCategoryId(id));
            List<BookVO> bookList = bookRepository.findBookByCategoryIdIn(idList, PageRequest.of(page, pageSize));
            ListResult<BookDto> result = new ListResult<>(new Meta(bookRepository.countBookByCategoryIdIn(idList).intValue(), new Paging(start, pageSize)), new ArrayList<>());
            result.setData(bookList.stream().map(e -> bookMapper.bookVOToBookDto(e)).collect(Collectors.toList()));
            return result;
        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Категории с указанным id в БД не обнаружено");
        }
    }

    public List<Long> getAllChildrenByCategoryId(Long id) {
        List<Long> childrenIds = categoryRepository.findIdByParentId(id);
        if (childrenIds != null && childrenIds.size() > 0) {
            List<Long> childrenChildrenIds = new ArrayList<>();
            for (Long childrenId: childrenIds) {
                childrenChildrenIds.addAll(getAllChildrenByCategoryId(childrenId));
            }
            childrenIds.addAll(childrenChildrenIds);
        }
        return childrenIds;
    }

    public ListResult<AuthorDto> getAuthorList(int start, int pageSize, String sort, String order) {
        ListResult<AuthorDto> result = new ListResult<>(new Meta((int) authorRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<AuthorDto> data = authorRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> authorMapper.toDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public ListResult<BookDto> getBookByAuthor(Long id, int start, int pageSize, String sort, String order) throws IllegalArgumentException {
        Optional<AuthorVO> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            int page = start / pageSize;
            List <BookDto> bookList = authorOptional.get().getBookAuthorsList().stream().map(e -> bookMapper.bookVOToBookDto(e.getBook())).collect(Collectors.toList());
            ListResult<BookDto> bookListResult = new ListResult<>(new Meta(bookList.size(), new Paging(start, pageSize)), new ArrayList<>());
            bookListResult.setData(bookList.subList(start, start + pageSize));
            return bookListResult;

        } else {
            throw new IllegalArgumentException("Ошибка запроса книги. Автора с указанным id в БД не обнаружено");
        }
    }

    public AuthorDto getAuthorById(Long id) {
        Optional<AuthorVO> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()){
            return authorMapper.toDto(authorOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса автора. Автора с указанным id в БД не обнаружено");
        }
    }

    public ListResult<PublisherDto> getPublisherList(int start, int pageSize, String sort, String order) {
        ListResult<PublisherDto> result = new ListResult<>(new Meta((int) publisherRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<PublisherDto> data = publisherRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.PublisherVOToPublisherDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public PublisherDto getPublisherById(Long publisherId) {
        Optional<PublisherVO> publisherOptional = publisherRepository.findById(publisherId);
        if (publisherOptional.isPresent()){
            return bookMapper.PublisherVOToPublisherDto(publisherOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса автора. Автора с указанным id в БД не обнаружено");
        }
    }

    public ListResult<CategoryDto> getCategoryList(int start, int pageSize, String sort, String order) {
        ListResult<CategoryDto> result = new ListResult<>(new Meta((int) categoryRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CategoryDto> data = categoryRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.CategoryVOToCategoryDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Optional<CategoryVO> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()){
            return bookMapper.CategoryVOToCategoryDto(categoryOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса автора. Автора с указанным id в БД не обнаружено");
        }
    }

    public BookSeriesDto getBookSeriesById(Long bookSeriesId) {
        Optional<BookSeriesVO> bookSeriesOptional = bookSeriesRepository.findById(bookSeriesId);
        if (bookSeriesOptional.isPresent()){
            return bookMapper.BookSeriesVOToBookSeriesDto(bookSeriesOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса книжной серии. Книжной серии с указанным id в БД не обнаружено");
        }
    }

    public ListResult<BookSeriesDto> getBookSeriesList(int start, int pageSize, String sort, String order) {
        ListResult<BookSeriesDto> result = new ListResult<>(new Meta((int) bookSeriesRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<BookSeriesDto> data = bookSeriesRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.BookSeriesVOToBookSeriesDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public CityDto getCityById(Long cityId) {
        Optional<CityVO> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isPresent()){
            return bookMapper.CityVOToCityDto(cityOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса города. Города с указанным id в БД не обнаружено");
        }
    }

    public ListResult<CityDto> getCityList(int start, int pageSize, String sort, String order) {
        ListResult<CityDto> result = new ListResult<>(new Meta((int) cityRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CityDto> data = cityRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.CityVOToCityDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public LanguageDto getLanguageById(Long languageId) {
        Optional<LanguageVO> languageOptional = languageRepository.findById(languageId);
        if (languageOptional.isPresent()){
            return bookMapper.LanguageVOToLanguageDto(languageOptional.get());
        } else {
            throw new IllegalArgumentException("Ошибка запроса языка. Языка с указанным id в БД не обнаружено");
        }
    }

    public ListResult<LanguageDto> getLanguageList(int start, int pageSize, String sort, String order) {
        ListResult<LanguageDto> result = new ListResult<>(new Meta((int) languageRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<LanguageDto> data = languageRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.LanguageVOToLanguageDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }
}
