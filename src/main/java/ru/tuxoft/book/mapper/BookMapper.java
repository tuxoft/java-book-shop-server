package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tuxoft.admin.dto.BookSeriesEditDto;
import ru.tuxoft.book.domain.*;
import ru.tuxoft.book.dto.*;
import ru.tuxoft.s3.domain.FileVO;
import ru.tuxoft.s3.domain.repository.FileRepository;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapperResolver.class})
public interface BookMapper {

    @Mappings({
            @Mapping(target = "coverUrl", source = "coverFile")
    })
    BookDto bookVOToBookDto(BookVO bookVO);

    @Mappings({
            @Mapping(target = "coverFile", source = "coverUrl")
    })
    BookVO bookDtoToBookVO(BookDto bookDto);

    default String CoverFileToCoverUrl(FileVO coverFile) {
        if (coverFile != null) {
            return "/api/file/s3/" + coverFile.getBucket() + "/" + coverFile.getKey() + "." + coverFile.getName().substring(coverFile.getName().lastIndexOf(".") + 1);
        } else {
            return "/api/file/s3/bookshop/notCoverImage.jpg";
        }
    }

    FileVO CoverFileToCoverUrl(String coverUrl);

    @Mapping(target = "bookList", ignore = true)
    AgeLimitDto AgeLimitVOToAgeLimitDto(AgeLimitVO ageLimitVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    AgeLimitVO AgeLimitDtoToAgeLimitVO(AgeLimitDto ageLimitDto);

    @Mapping(target = "bookList", ignore = true)
    CoverTypeDto CoverTypeVOToCoverTypeDto(CoverTypeVO coverTypeVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    CoverTypeVO CoverTypeDtoToCoverTypeVO(CoverTypeDto coverTypeDto);

    @Mappings({
            @Mapping(target = "bookAuthorsList", ignore = true),
            @Mapping(target = "firstName", ignore = true),
            @Mapping(target = "middleName", ignore = true),
            @Mapping(target = "lastName", ignore = true),
    })
    AuthorVO AuthorDtoToAuthorVO(AuthorDto authorDto);

    @Mappings({
            @Mapping(target = "book", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    BookAuthorsVO BookAuthorsDtoToBookAuthorsVO(BookAuthorsDto bookAuthorsDto);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "bookSeriesList", ignore = true)
    })

    PublisherDto PublisherVOToPublisherDto(PublisherVO publisherVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    PublisherVO PublisherDtoToPublisherVO(PublisherDto publisherDto);

    @Mapping(target = "bookList", ignore = true)
    CityDto CityVOToCityDto(CityVO cityVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    CityVO CityDtoToCityVO(CityDto cityDto);

    @Mapping(target = "bookList", ignore = true)
    CategoryDto CategoryVOToCategoryDto(CategoryVO categoryVO);

    @Mappings({
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "bookList", ignore = true)
    })
    CategoryVO CategoryDtoToCategoryVO(CategoryDto categoryDto);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "publisherId", source = "publisher.id")
    })
    BookSeriesDto BookSeriesVOToBookSeriesDto(BookSeriesVO bookSeriesVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    BookSeriesVO BookSeriesDtoToBookSeriesVO(BookSeriesDto bookSeriesDto);

    @Mapping(target = "bookList", ignore = true)
    LanguageDto LanguageVOToLanguageDto(LanguageVO languageVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true)
    })
    LanguageVO LanguageDtoToLanguageVO(LanguageDto languageDto);

    BookSeriesEditDto bookSeriesVOToBookSeriesEditDto(BookSeriesVO bookSeriesVO);

    @Mappings({
            @Mapping(target = "bookList", ignore = true),
            @Mapping(target = "deleted", ignore = true),
    })
    BookSeriesVO bookSeriesEditDtoToBookSeriesVO(BookSeriesEditDto bookSeriesEditDto);

}
