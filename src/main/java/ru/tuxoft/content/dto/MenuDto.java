package ru.tuxoft.content.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuDto {

    private List<MenuItemDto> top;

    private List<MenuItemDto> footer;

}
