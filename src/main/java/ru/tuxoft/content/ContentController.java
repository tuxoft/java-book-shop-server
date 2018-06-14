package ru.tuxoft.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.book.dto.CategoryDto;
import ru.tuxoft.content.dto.MenuDto;
import ru.tuxoft.content.dto.MenuItemDto;
import ru.tuxoft.content.dto.PromoPictureDto;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class ContentController {

    @Autowired
    ContentService contentService;

    @RequestMapping(method = RequestMethod.GET, path = "/menu")
    public MenuDto getMenu() throws IOException {
        return contentService.getMenu(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categoryCarousels")
    public List<CategoryDto> getCategoriesListForCarousel() {
        return contentService.getCategoriesListForCarousel(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/promoPictures")
    public List<PromoPictureDto> getPromoPictures() {
        return contentService.getPromoPictures(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/navigationMenuTop/{id}")
    public List<MenuItemDto> getCategoryNavigationMenuTopItemList(@PathVariable("id") Long categoryId) {
        return contentService.getCategoryNavigationMenuTopItemList(categoryId, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/navigationMenuLeft/{id}")
    public List<MenuItemDto> getCategoryNavigationMenuLeftItemList(@PathVariable("id") Long categoryId) {
        return contentService.getCategoryNavigationMenuLeftItemList(categoryId, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/userMenu")
    public List<MenuItemDto> getUserMenuItemList() throws IOException {
        return contentService.getUserMenuItemList(SecurityContextHolder.getContext().getAuthentication().getName(), (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }
}
