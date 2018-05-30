package ru.tuxoft.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tuxoft.book.domain.CategoryVO;
import ru.tuxoft.book.domain.repository.CategoryRepository;
import ru.tuxoft.book.dto.BookDto;
import ru.tuxoft.book.dto.CategoryDto;
import ru.tuxoft.book.mapper.BookMapper;
import ru.tuxoft.book.mapper.CategoryMapper;
import ru.tuxoft.content.domain.repository.PromoPictureRepository;
import ru.tuxoft.content.dto.MenuDto;
import ru.tuxoft.content.dto.MenuItemDto;
import ru.tuxoft.content.dto.PromoPictureDto;
import ru.tuxoft.paging.ListResult;
import ru.tuxoft.paging.Meta;
import ru.tuxoft.paging.Paging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContentService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PromoPictureRepository promoPictureRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    CategoryMapper categoryMapper;

    public MenuDto getMenu(String userId) throws IOException {
        MenuDto menu = new MenuDto();

        List<MenuItemDto> menuTopList = getMenuItemByCategory(null, 3);

        List<MenuItemDto> menuFooterList = getMenuFromJson("menu/footer.json");

        menu.setTop(menuTopList);
        menu.setFooter(menuFooterList);
        return menu;
    }

    private List<MenuItemDto> getMenuFromJson(String fileName) throws IOException {
        List<MenuItemDto> result;

        InputStream is = ContentController.class.getClassLoader().getResourceAsStream(fileName);
        ObjectMapper mapper = new ObjectMapper();
        result = mapper.readValue(is, new TypeReference<List<MenuItemDto>>(){});

        return result;
    }

    private List<MenuItemDto> getMenuItemByCategory(Long categoryId, int countEnclosureLevel) {
        List<MenuItemDto> result = new ArrayList<>();

        List<CategoryVO> menuItems = categoryRepository.findByParentId(categoryId);
        for (CategoryVO menuItem: menuItems) {
            MenuItemDto menuItemDto;
            if (countEnclosureLevel>1) {
                menuItemDto = new MenuItemDto(menuItem, false);
                menuItemDto.setSubItems(getMenuItemByCategory(menuItem.getId(), countEnclosureLevel - 1));
            } else {
                menuItemDto = new MenuItemDto(menuItem, true);
            }
            result.add(menuItemDto);
        }
        return result;
    }


    public List<CategoryDto> getCategoriesListForCarousel(String userId) {
        return categoryRepository.findByParentId(3L).stream().map(e -> {
            CategoryDto categoryDto = categoryMapper.categoryVOToCategoryDto(e);
            List<BookDto> bookDtoList = e.getBookList().stream().map(bookVO -> bookMapper.bookVOToBookDto(bookVO)).collect(Collectors.toList());
            categoryDto.setBookList(bookDtoList);
            return categoryDto;
        }).collect(Collectors.toList());
    }

    public List<PromoPictureDto> getPromoPictures(String userId) {
        return promoPictureRepository.findByDeletedIsFalse().stream().map(promoPictureVO -> new PromoPictureDto(promoPictureVO)).collect(Collectors.toList());
    }

    public List<MenuItemDto> getCategoryNavigationMenuTopItemList(Long categoryId, String userId) {
        List<MenuItemDto> result = new ArrayList<>();
        Optional<CategoryVO> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            MenuItemDto menuItem = new MenuItemDto(category.get(), true);
            result.add(0, menuItem);
            Long parentId = category.get().getParentId();
            if (parentId != null) {
                do {
                    Optional<CategoryVO> parentCategory = categoryRepository.findById(parentId);
                    if (parentCategory.isPresent()) {
                        MenuItemDto nextMenuItem = new MenuItemDto(parentCategory.get(), true);
                        result.add(0, nextMenuItem);
                        parentId = parentCategory.get().getParentId();
                    } else {
                        throw new IllegalArgumentException("Ошибка запроса навигационного меню. В БД не найден parentId");
                    }
                } while (parentId != null);
            }
        } else {
            throw new IllegalArgumentException("Ошибка запроса навигационного меню. Категории с указанным id в БД не обнаружено");
        }
        return result;
    }

    public List<MenuItemDto> getCategoryNavigationMenuLeftItemList(Long categoryId, String userId) {
        List<MenuItemDto> result = new ArrayList<>();

        Optional<CategoryVO> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            MenuItemDto menuItem = new MenuItemDto(category.get(), true);
            List<CategoryVO> childCategoryList = categoryRepository.findByParentId(categoryId);
            if (childCategoryList.size()>0) {
                for (CategoryVO childCategory: childCategoryList) {
                    MenuItemDto childMenuItem = new MenuItemDto(childCategory, true);
                    menuItem.getSubItems().add(childMenuItem);
                }
                result.add(menuItem);
            }

        } else {
            throw new IllegalArgumentException("Ошибка запроса навигационного меню. Категории с указанным id в БД не обнаружено");
        }
        return result;
    }

    public MenuDto getAdminMenu() throws IOException {
        MenuDto menu = new MenuDto();

        List<MenuItemDto> menuTopList = getMenuFromJson("menu/adminHeader.json");

        List<MenuItemDto> menuFooterList = getMenuFromJson("menu/adminFooter.json");

        menu.setTop(menuTopList);
        menu.setFooter(menuFooterList);
        return menu;

    }

}
