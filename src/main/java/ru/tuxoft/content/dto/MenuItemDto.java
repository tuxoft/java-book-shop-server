package ru.tuxoft.content.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.book.domain.CategoryVO;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuItemDto {

    private String name;

    private String url;

    private List<MenuItemDto> subItems;

    public MenuItemDto(CategoryVO menuItem, Boolean needSetUrl) {
        this.name = menuItem.getName();
        if (needSetUrl) {
            this.url = "/categories/" + menuItem.getId();
        }
    }
}
