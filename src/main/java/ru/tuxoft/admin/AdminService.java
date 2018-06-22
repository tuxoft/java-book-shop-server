package ru.tuxoft.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tuxoft.admin.dto.*;
import ru.tuxoft.book.BookService;
import ru.tuxoft.book.domain.*;
import ru.tuxoft.book.domain.repository.*;
import ru.tuxoft.book.dto.*;
import ru.tuxoft.book.mapper.*;
import ru.tuxoft.cart.domain.repository.CartItemRepository;
import ru.tuxoft.content.domain.CategoryCarouselVO;
import ru.tuxoft.content.domain.PromoPictureVO;
import ru.tuxoft.content.domain.repository.CategoryCarouselRepository;
import ru.tuxoft.content.domain.repository.PromoPictureRepository;
import ru.tuxoft.order.domain.repository.OrderItemRepository;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;
import ru.tuxoft.s3.S3Service;
import ru.tuxoft.s3.domain.FileVO;
import ru.tuxoft.s3.domain.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminService {

    @Value("${s3.bucket}")
    private String bucket;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookSeriesRepository bookSeriesRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookAuthorsRepository bookAuthorsRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    S3Service s3Service;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    BookService bookService;

    @Autowired
    PromoPictureRepository promoPictureRepository;

    @Autowired
    PromoPictureMapper promoPictureMapper;

    @Autowired
    CategoryCarouselRepository categoryCarouselRepository;

    @Autowired
    CategoryCarouselMapper categoryCarouselMapper;

    public BookDto updateBook(BookDto bookDto) {
        BookVO updateBook = bookMapper.bookDtoToBookVO(bookDto);
        updateBook = bookRepository.saveAndFlush(updateBook);
        setBookAuthors(updateBook);
        return bookMapper.bookVOToBookDto(updateBook);
    }

    public void setBookAuthors(BookVO book) {
        bookAuthorsRepository.deleteByBookId(book.getId());
        for (BookAuthorsVO bookAuthors: book.getBookAuthors()) {
            BookAuthorsVO findBookAuthors = new BookAuthorsVO();
            findBookAuthors.setBook(book);
            findBookAuthors.setAuthor(authorRepository.findById(bookAuthors.getAuthor().getId()).get());
            findBookAuthors.setPosition(bookAuthors.getPosition());
            bookAuthorsRepository.saveAndFlush(findBookAuthors);
        }
    }

    public String uploadFile(MultipartFile file) {
        FileVO fileVO = new FileVO();
        fileVO.setName(file.getOriginalFilename());
        fileVO.setFileSize(file.getSize());
        fileVO.setMimeType(file.getContentType());
        fileVO.setBucket(bucket);
        String key = s3Service.uploadFile(file);
        fileVO.setKey(key);
        fileRepository.saveAndFlush(fileVO);
        return "/api/file/s3/" + bucket + "/" + fileVO.getKey() + fileVO.getName().substring(fileVO.getName().lastIndexOf("."));
    }

    public AuthorDto updateAuthor(AuthorDto authorDto) {
        AuthorVO updateAuthor = authorMapper.toVO(authorDto);
        updateAuthor = authorRepository.saveAndFlush(updateAuthor);
        return authorMapper.toDto(updateAuthor);
    }

    public PublisherDto updatePublisher(PublisherDto publisherDto) {
        PublisherVO updatePublisher = bookMapper.PublisherDtoToPublisherVO(publisherDto);
        updatePublisher = publisherRepository.saveAndFlush(updatePublisher);
        return bookMapper.PublisherVOToPublisherDto(updatePublisher);
    }

    public CategoryEditDto updateCategory(CategoryEditDto categoryDto) {
        CategoryVO updateCategory = categoryMapper.categoryEditDtoToCategoryVO(categoryDto);
        updateCategory = categoryRepository.saveAndFlush(updateCategory);
        return categoryMapper.categoryVOToCategoryEditDto(updateCategory);
    }

    public BookSeriesEditDto updateBookSeries(BookSeriesEditDto bookSeriesDto) {
        BookSeriesVO updateBookSeries = bookMapper.bookSeriesEditDtoToBookSeriesVO(bookSeriesDto);
        updateBookSeries = bookSeriesRepository.saveAndFlush(updateBookSeries);
        return bookMapper.bookSeriesVOToBookSeriesEditDto(updateBookSeries);
    }

    public CityDto updateCity(CityDto cityDto) {
        CityVO updateCity = bookMapper.CityDtoToCityVO(cityDto);
        updateCity = cityRepository.saveAndFlush(updateCity);
        return bookMapper.CityVOToCityDto(updateCity);
    }

    public LanguageDto updateLanguage(LanguageDto languageDto) {
        LanguageVO updateLanguage = bookMapper.LanguageDtoToLanguageVO(languageDto);
        updateLanguage = languageRepository.saveAndFlush(updateLanguage);
        return bookMapper.LanguageVOToLanguageDto(updateLanguage);
    }

    public CategoryEditDto getCategoryEditById(Long categoryId) {
        Optional<CategoryVO> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()){
            CategoryEditDto categoryEditDto = categoryMapper.categoryVOToCategoryEditDto(categoryOptional.get());
            return categoryEditDto;
        } else {
            throw new IllegalArgumentException("Ошибка запроса автора. Автора с указанным id в БД не обнаружено");
        }
    }

    public ListResult<CategoryEditDto> getCategoryEditList(int start, int pageSize, String sort, String order) {
        ListResult<CategoryEditDto> result = new ListResult<>(new Meta((int) categoryRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;

        List<CategoryEditDto> data = categoryRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> categoryMapper.categoryVOToCategoryEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public BookSeriesEditDto getBookSeriesEditById(Long bookSeriesId) {
        Optional<BookSeriesVO> bookSeriesOptional = bookSeriesRepository.findById(bookSeriesId);
        if (bookSeriesOptional.isPresent()){
            BookSeriesEditDto bookSeriesEditDto = bookMapper.bookSeriesVOToBookSeriesEditDto(bookSeriesOptional.get());
            return bookSeriesEditDto;
        } else {
            throw new IllegalArgumentException("Ошибка запроса книжной серии. Книжной серии с указанным id в БД не обнаружено");
        }
    }

    public ListResult<BookSeriesEditDto> getBookSeriesEditList(int start, int pageSize, String sort, String order) {
        ListResult<BookSeriesEditDto> result = new ListResult<>(new Meta((int) bookSeriesRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<BookSeriesEditDto> data = bookSeriesRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> bookMapper.bookSeriesVOToBookSeriesEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;

    }

    public void deleteBookById(Long bookId) {
        cartItemRepository.deleteByBookId(bookId);
        orderItemRepository.deleteByBookId(bookId);
        bookRepository.deleteById(bookId);
    }

    public void deleteAuthorById(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    public void deletePublisherById(Long publisherId) {
        List<BookVO> updateBooks = bookRepository.findBookByPublisherId(publisherId);
        for (BookVO bookVO: updateBooks) {
            bookVO.setPublisher(null);
            bookVO.setBookSeries(null);
            bookRepository.saveAndFlush(bookVO);
        }
        publisherRepository.deleteById(publisherId);
    }

    public void deleteCategoryById(Long categoryId) {
        List<Long> childCategoryIdList = bookService.getAllChildrenByCategoryId(categoryId);
        childCategoryIdList.add(categoryId);
        categoryRepository.deleteByIdIn(childCategoryIdList);
    }

    public void deleteBookSeriesById(Long bookSeriesId) {
        List<BookVO> updateBooks = bookRepository.findBookByBookSeriesId(bookSeriesId);
        for (BookVO bookVO: updateBooks) {
            bookVO.setBookSeries(null);
            bookRepository.saveAndFlush(bookVO);
        }
        bookSeriesRepository.deleteById(bookSeriesId);
    }

    public void deleteCityById(Long cityId) {
        List<BookVO> updateBooks = bookRepository.findBookByCityId(cityId);
        for (BookVO bookVO: updateBooks) {
            bookVO.setCity(null);
            bookRepository.saveAndFlush(bookVO);
        }
        cityRepository.deleteById(cityId);
    }

    public void deleteLanguageById(Long languageId) {
        List<BookVO> updateBooks = bookRepository.findBookByLanguageId(languageId);
        for (BookVO bookVO: updateBooks) {
            bookVO.setLanguage(null);
            bookRepository.saveAndFlush(bookVO);
        }
        languageRepository.deleteById(languageId);
    }

    public PromoPictureEditDto getPromoPictureById(Long promoPictureId) {
        Optional<PromoPictureVO> promoPictureOptional = promoPictureRepository.findById(promoPictureId);
        if (promoPictureOptional.isPresent()){
            PromoPictureEditDto promoPictureEditDto = promoPictureMapper.promoPictureVOToPromoPictureEditDto(promoPictureOptional.get());
            return promoPictureEditDto;
        } else {
            throw new IllegalArgumentException("Ошибка запроса промо-картинки. Промо-картинки с указанным id в БД не обнаружено");
        }
    }

    public PromoPictureEditDto updatePromoPicture(PromoPictureEditDto promoPictureDto) {
        PromoPictureVO updatePromoPicture = promoPictureMapper.promoPictureEditDtoToPromoPictureVO(promoPictureDto);
        updatePromoPicture = promoPictureRepository.saveAndFlush(updatePromoPicture);
        return promoPictureMapper.promoPictureVOToPromoPictureEditDto(updatePromoPicture);
    }

    public void deletePromoPictureById(Long promoPictureId) {
        promoPictureRepository.deleteById(promoPictureId);
    }

    public ListResult<PromoPictureEditDto> getPromoPictureEditList(int start, int pageSize, String sort, String order) {
        ListResult<PromoPictureEditDto> result = new ListResult<>(new Meta((int) promoPictureRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<PromoPictureEditDto> data = promoPictureRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> promoPictureMapper.promoPictureVOToPromoPictureEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    public CategoryCarouselEditDto getCategoryCarouselById(Long categoryCarouselId) {
        Optional<CategoryCarouselVO> categoryCarouselOptional = categoryCarouselRepository.findById(categoryCarouselId);
        if (categoryCarouselOptional.isPresent()){
            CategoryCarouselEditDto categoryCarouselEditDto = categoryCarouselMapper.categoryCarouselVOToCategoryCarouselEditDto(categoryCarouselOptional.get());
            return categoryCarouselEditDto;
        } else {
            throw new IllegalArgumentException("Ошибка запроса карусели-категории. Карусели-категории с указанным id в БД не обнаружено");
        }
    }

    public CategoryCarouselEditDto updateCategoryCarousel(CategoryCarouselEditDto categoryCarouselDto) {
        CategoryCarouselVO updateCategoryCarousel = categoryCarouselMapper.categoryCarouselEditDtoToCategoryCarouselVO(categoryCarouselDto);
        updateCategoryCarousel = categoryCarouselRepository.saveAndFlush(updateCategoryCarousel);
        return categoryCarouselMapper.categoryCarouselVOToCategoryCarouselEditDto(updateCategoryCarousel);

    }

    public void deleteCategoryCarouselById(Long categoryCarouselId) {
        categoryCarouselRepository.deleteById(categoryCarouselId);
    }

    public ListResult<CategoryCarouselEditDto> getCategoryCarouselEditList(int start, int pageSize, String sort, String order) {
        ListResult<CategoryCarouselEditDto> result = new ListResult<>(new Meta((int) categoryCarouselRepository.count(), new Paging(start, pageSize)), new ArrayList<>());
        int page = start / pageSize;
        List<CategoryCarouselEditDto> data = categoryCarouselRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sort)).stream().map(e -> categoryCarouselMapper.categoryCarouselVOToCategoryCarouselEditDto(e)).collect(Collectors.toList());
        result.setData(data);
        return result;
    }
}
