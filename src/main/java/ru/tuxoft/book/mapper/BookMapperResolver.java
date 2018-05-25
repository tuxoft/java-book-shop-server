package ru.tuxoft.book.mapper;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tuxoft.book.domain.AuthorVO;
import ru.tuxoft.book.domain.BookAuthorsVO;
import ru.tuxoft.book.domain.repository.AuthorRepository;
import ru.tuxoft.book.dto.AuthorDto;
import ru.tuxoft.book.dto.BookAuthorsDto;
import ru.tuxoft.s3.domain.FileVO;
import ru.tuxoft.s3.domain.repository.FileRepository;

import java.util.Optional;

@Component
public class BookMapperResolver {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @ObjectFactory
    public FileVO resolve(String coverUrl, @TargetType Class<FileVO> type) {
        String key = coverUrl.substring(coverUrl.lastIndexOf("/") + 1,coverUrl.lastIndexOf("."));
        return fileRepository.findByKey(key);
    }

    @ObjectFactory
    public AuthorVO resolve(AuthorDto authorDto, @TargetType Class<AuthorVO> type) {
        Optional<AuthorVO> authorOptional = authorRepository.findById(authorDto.getId());
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        } else {
            return null;
        }
    }

}
