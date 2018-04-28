package ru.tuxoft.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер S3
 */
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    /**
     * Отдает файл из S3
     *
     * @param backet   Бакет в котором находится файл
     * @param s3key    Ключ в s3
     * @param ext      расширение файла
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/file/s3/{backet}/{s3key}.{ext}")
    public ResponseEntity getPicture(
            @PathVariable(value = "backet", required = true) String backet,
            @PathVariable(value = "s3key", required = true) String s3key,
            @PathVariable(value = "ext", required = false) String ext,
            HttpServletResponse response) {
        boolean write = s3Service.writeFileToHttpServletResponse(response, backet, s3key, ext);
        if (write) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
