package ru.tuxoft.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.repository.*;
import ru.tuxoft.dictionary.domain.DictionaryTypeEnum;
import ru.tuxoft.dictionary.dto.DictionaryDto;
import ru.tuxoft.order.domain.repository.ShopCityRepository;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DictionaryService {

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
    AgeLimitRepository ageLimitRepository;

    @Autowired
    CoverTypeRepository coverTypeRepository;

    @Autowired
    ShopCityRepository shopCityRepository;

    public ListResult<DictionaryDto> getDictionary(String dictionary, Long parentId, List<Long> idList) {
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(-1, new Paging(0, -1)), new ArrayList<>());
        if (idList == null) {
            if (dictionary.equals(DictionaryTypeEnum.PUBLISHER.getType())) {
                result.setData(publisherRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.AUTHOR.getType())) {
                result.setData(authorRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.BOOKSERIES.getType())) {
                result.setData(bookSeriesRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.CATEGORY.getType())) {
                result.setData(categoryRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.CITY.getType())) {
                result.setData(cityRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.LANGUAGE.getType())) {
                result.setData(languageRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.AGELIMIT.getType())) {
                result.setData(ageLimitRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.COVERTYPE.getType())) {
                result.setData(coverTypeRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.SHOPCITY.getType())) {
                result.setData(shopCityRepository.findAll().stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            }
        } else {
            if (dictionary.equals(DictionaryTypeEnum.PUBLISHER.getType())) {
                result.setData(publisherRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.AUTHOR.getType())) {
                result.setData(authorRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.BOOKSERIES.getType())) {
                result.setData(bookSeriesRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.CATEGORY.getType())) {
                result.setData(categoryRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.CITY.getType())) {
                result.setData(cityRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.LANGUAGE.getType())) {
                result.setData(languageRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.AGELIMIT.getType())) {
                result.setData(ageLimitRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.COVERTYPE.getType())) {
                result.setData(coverTypeRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            } else if (dictionary.equals(DictionaryTypeEnum.SHOPCITY.getType())) {
                result.setData(shopCityRepository.findByIdIn(idList).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
            }
        }
        result.getMeta().setTotal(result.getData().size());
        result.getMeta().getPaging().setPageSize(result.getData().size());
        return result;
    }

    public ListResult<DictionaryDto> searchDictionary(String dictionary, String query, Long parentId, int start, int pageSize) {
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(-1, new Paging(start, pageSize)), new ArrayList<>());
        query = query.toLowerCase().trim();
        if (dictionary.equals(DictionaryTypeEnum.PUBLISHER.getType())) {
            result = searchPublisher(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.AUTHOR.getType())) {
            result = searchAuthor(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.BOOKSERIES.getType())) {
            result = searchBookSeries(query, parentId, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.CATEGORY.getType())) {
            result = searchCategories(query, parentId, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.CITY.getType())) {
            result = searchCity(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.LANGUAGE.getType())) {
            result = searchLanguage(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.AGELIMIT.getType())) {
            result = searchAgeLimit(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.COVERTYPE.getType())) {
            result = searchCoverType(query, start, pageSize);
        } else if (dictionary.equals(DictionaryTypeEnum.SHOPCITY.getType())) {
            result = searchShopCity(query, start, pageSize);
        }
        return result;
    }

    private ListResult<DictionaryDto> searchShopCity(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(shopCityRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(shopCityRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchCoverType(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(coverTypeRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(coverTypeRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchAgeLimit(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(ageLimitRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(ageLimitRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchLanguage(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(languageRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(languageRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchCity(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(cityRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(cityRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchCategories(String query, Long parentId, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(categoryRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(categoryRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchBookSeries(String query, Long parentId, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(bookSeriesRepository.findCountByNameLikeAndPublisherId(query, parentId), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(bookSeriesRepository.findByNameLikeAndPublisherId(query, parentId, PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchAuthor(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(authorRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(authorRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }

    private ListResult<DictionaryDto> searchPublisher(String query, int start, int pageSize) {
        int page = start / pageSize;
        ListResult<DictionaryDto> result = new ListResult<>(new Meta(publisherRepository.findCountByNameLike(query), new Paging(start, pageSize)), new ArrayList<>());
        result.setData(publisherRepository.findByNameLike(query,  PageRequest.of(page, pageSize)).stream().map(e -> new DictionaryDto(e)).collect(Collectors.toList()));
        return result;
    }
}
