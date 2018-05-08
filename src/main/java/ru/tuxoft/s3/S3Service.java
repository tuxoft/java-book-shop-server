package ru.tuxoft.s3;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;
import ru.tuxoft.s3.domain.FileVO;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Сервис для работы с файловым хранилищем s3
 */
public interface S3Service {

    byte[] downloadFileAsBytes(String keyName, String bucketName);

    InputStream downloadFile(String keyName, String bucketName);

    S3Object getS3Object(String keyName, String bucketName);

    boolean writeFileToHttpServletResponse(HttpServletResponse response, String bucketName, String keyName, String ext);

    String uploadFile(String bucketName, byte[] data, String contentType, String originalName);

    String uploadFile(String keyName, String bucketName, byte[] data, String contentType, String originalName);

    String uploadFile(FileVO fileVO, byte[] data);

    String uploadFile(MultipartFile file);
}