package ru.tuxoft.book.mapper;

import org.mapstruct.Mapper;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.dto.AuthorDto;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(AuthorVO vo);

    AuthorVO toVO(AuthorDto dto);

}
