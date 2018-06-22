package ru.tuxoft.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tuxoft.dictionary.dto.DictionaryDto;
import ru.tuxoft.paging.ListResult;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
@Slf4j
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public ListResult<DictionaryDto> getDictionary(
            @RequestParam(name = "dictionary") String dictionary,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "idList[]", required = false) List<Long> idList
    ) {
        return dictionaryService.getDictionary(dictionary, parentId, idList);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public ListResult<DictionaryDto> searchDictionary(
            @RequestParam(name = "dictionary") String dictionary,
            @RequestParam(name = "query") String query,
            @RequestParam(name = "parentId", required = false) Long parentId,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        return dictionaryService.searchDictionary(dictionary, query, parentId, start, pageSize);
    }
}
