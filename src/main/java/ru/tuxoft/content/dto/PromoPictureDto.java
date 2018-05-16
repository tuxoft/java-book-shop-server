package ru.tuxoft.content.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tuxoft.content.domain.PromoPictureVO;

@Data
@NoArgsConstructor
public class PromoPictureDto {

    String url;

    String pictureUrl;

    public PromoPictureDto(PromoPictureVO promoPictureVO) {

        this.url = promoPictureVO.getUrl();

        if (promoPictureVO.getPicture() != null) {
            this.pictureUrl = "/api/file/s3/" + promoPictureVO.getPicture().getBucket() + "/" + promoPictureVO.getPicture().getKey() + "." + promoPictureVO.getPicture().getName().substring(promoPictureVO.getPicture().getName().lastIndexOf(".") + 1);
        }
    }
}
