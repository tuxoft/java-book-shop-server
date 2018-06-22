package ru.tuxoft.dictionary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.dictionary.domain.DictionaryTypeEnum;
import ru.tuxoft.book.domain.*;
import ru.tuxoft.order.domain.ShopCityVO;

@Data
@NoArgsConstructor
public class DictionaryDto {

    Long id;

    String name;

    Long parentId;

    String type;

    public DictionaryDto(PublisherVO publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.parentId = null;
        this.type = DictionaryTypeEnum.PUBLISHER.getType();
    }

    public DictionaryDto(AuthorVO author) {
        this.id = author.getId();
        this.name = "";
        if (author.getFirstName() != null) {
            this.name += author.getFirstName();
        }
        if (author.getMiddleName() != null) {
            this.name += " " + author.getMiddleName();
        }
        if (author.getLastName() != null) {
            this.name += " " + author.getLastName();
        }
        this.name = this.getName().trim();
        this.parentId = null;
        this.type = DictionaryTypeEnum.AUTHOR.getType();
    }

    public DictionaryDto(BookSeriesVO bookSeries) {
        this.id = bookSeries.getId();
        this.name = bookSeries.getName();
        this.parentId = bookSeries.getPublisher().getId();
        this.type = DictionaryTypeEnum.BOOKSERIES.getType();
    }

    public DictionaryDto(CategoryVO category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentId();
        this.type = DictionaryTypeEnum.CATEGORY.getType();
    }

    public DictionaryDto(CityVO city) {
        this.id = city.getId();
        this.name = city.getName();
        this.type = DictionaryTypeEnum.CITY.getType();
    }

    public DictionaryDto(LanguageVO language) {
        this.id = language.getId();
        this.name = language.getName();
        this.type = DictionaryTypeEnum.LANGUAGE.getType();
    }

    public DictionaryDto(CoverTypeVO coverType) {
        this.id = coverType.getId();
        this.name = coverType.getName();
        this.type = DictionaryTypeEnum.COVERTYPE.getType();
    }

    public DictionaryDto(AgeLimitVO ageLimit) {
        this.id = ageLimit.getId();
        this.name = ageLimit.getName();
        this.type = DictionaryTypeEnum.AGELIMIT.getType();
    }

    public DictionaryDto(ShopCityVO shopCity) {
        this.id = shopCity.getId();
        this.name = shopCity.getName();
        this.type = DictionaryTypeEnum.SHOPCITY.getType();
    }
}
