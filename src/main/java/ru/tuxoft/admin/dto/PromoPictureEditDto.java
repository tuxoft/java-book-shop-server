package ru.tuxoft.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromoPictureEditDto {

    Long id;

    String url;

    String pictureUrl;

    Boolean active;

}
