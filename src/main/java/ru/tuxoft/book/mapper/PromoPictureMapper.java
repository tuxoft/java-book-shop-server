package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.tuxoft.admin.dto.PromoPictureEditDto;
import ru.tuxoft.content.domain.PromoPictureVO;
import ru.tuxoft.content.dto.PromoPictureDto;
import ru.tuxoft.s3.domain.FileVO;

@Mapper(componentModel = "spring", uses = {BookMapperResolver.class})
public interface PromoPictureMapper {

    @Mappings({
            @Mapping(target = "picture", source = "pictureUrl"),
            @Mapping(target = "deleted", ignore = true),
    })
    PromoPictureVO promoPictureEditDtoToPromoPictureVO(PromoPictureEditDto promoPictureEditDto);

    @Mappings({
            @Mapping(target = "pictureUrl", source = "picture")
    })
    PromoPictureEditDto promoPictureVOToPromoPictureEditDto(PromoPictureVO promoPictureVO);

    @Mappings({
            @Mapping(target = "pictureUrl", source = "picture")
    })
    PromoPictureDto promoPictureVOToPromoPictureDto(PromoPictureVO promoPictureVO);

    default String PictureFileToPictureUrl(FileVO pictureFile) {
        if (pictureFile != null) {
            return "/api/file/s3/" + pictureFile.getBucket() + "/" + pictureFile.getKey() + "." + pictureFile.getName().substring(pictureFile.getName().lastIndexOf(".") + 1);
        } else {
            return null;
        }
    }

    FileVO PictureUrlToPictureFile(String coverUrl);
}
