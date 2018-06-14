package ru.tuxoft.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryCarouselEditDto {

    Long id;

    CategoryEditDto category;

    Boolean active;

}
