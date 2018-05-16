package ru.tuxoft.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.book.dto.CategoryDto;
import ru.tuxoft.content.dto.MenuDto;
import ru.tuxoft.content.dto.PromoPictureDto;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class ContentController {

    private String userId = "user1";

    @Autowired
    ContentService contentService;

    @RequestMapping(method = RequestMethod.GET, path = "/menu")
    public MenuDto getMenu() throws IOException {
        return contentService.getMenu(userId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categoryCarousels")
    public List<CategoryDto> getCategoriesListForCarousel() {
        return contentService.getCategoriesListForCarousel(userId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/promoPictures")
    public List<PromoPictureDto> getPromoPictures() {
        return contentService.getPromoPictures(userId);
    }
}
