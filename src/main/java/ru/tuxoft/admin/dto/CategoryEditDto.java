package ru.tuxoft.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryEditDto {

    private Long id;

    private String name;

    private CategoryEditDto parent;

}
