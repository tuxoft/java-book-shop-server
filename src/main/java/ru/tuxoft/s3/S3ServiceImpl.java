package ru.tuxoft.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с файловым хранилищем s3
 */
@Slf4j
@Component
public class S3ServiceImpl implements S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucket;

    /**
     * Скачать файл из S3
     *
     * @param keyName ключь файла в S3
     * @return файл
     */
    @Override
    public byte[] downloadFileAsBytes(String keyName, String bucketName) {
        InputStream is = downloadFile(keyName, bucketName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            IOUtils.copy(is, os);
            return os.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public InputStream downloadFile(String keyName, String bucketName) {
        S3Object s3object = getS3Object(keyName, bucketName);
        return s3object.getObjectContent();
    }

    @Override
    public S3Object getS3Object(String keyName, String bucketName) {
        try {
            return s3client.getObject(new GetObjectRequest(bucketName, keyName));
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.error(ase.getMessage(), ase);
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + ace.getMessage());
            log.error(ace.getMessage(), ace);
        }
        return null;
    }

    @Override
    public boolean writeFileToHttpServletResponse(HttpServletResponse response, String bucketName, String keyName, String ext) {
        S3Object s3object = getS3Object(keyName, bucketName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            IOUtils.copy(s3object.getObjectContent(), os);
            byte[] data = os.toByteArray();
            String originalName = null;
            Map<String, String> userMeta = s3object.getObjectMetadata().getUserMetadata();
            if (userMeta != null) {
                originalName = userMeta.get("name");
            }
            if (originalName == null) {
                originalName = UUID.randomUUID().toString();
                if (ext != null && !ext.isEmpty())
                    originalName += "." + ext;
            }
            long contentLength = s3object.getObjectMetadata().getContentLength();
            String contentType = s3object.getObjectMetadata().getContentType();
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "inline; filename*=UTF-8''" + URLEncoder.encode(originalName, "UTF-8").replace("+", "%20"));
            if (data.length <= Integer.MAX_VALUE) {
                response.setContentLength((int) data.length);
            } else {
                response.addHeader("Content-Length", Long.toString(data.length));
            }
            ServletOutputStream out = response.getOutputStream();
            out.write(data);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * залить файл в S3
     *
     * @param data контент
     * @return ключь файла в S3
     */
    @Override
    public String uploadFile(String bucketName, byte[] data, String contentType, String originalName) {
        return uploadFile(UUID.randomUUID().toString(), bucketName, data, contentType, originalName);
    }

    /**
     * @param keyName ключ файла для залития в S3
     * @param data    контент
     * @return ключь файла в S3
     */
    @Override
    public String uploadFile(String keyName, String bucketName, byte[] data, String contentType, String originalName) {
        InputStream is = new ByteArrayInputStream(data);
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(data.length);
            meta.setContentType(contentType);
            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put("name", originalName);
            meta.setUserMetadata(userMetadata);
            s3client.putObject(new PutObjectRequest(bucketName, keyName, is, meta));
            return keyName;
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.error(ase.getMessage(), ase);
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + ace.getMessage());
            log.error(ace.getMessage(), ace);
        }
        return keyName;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        try {
            byte[] content = multipartFile.getBytes();
            String name = multipartFile.getName();
            String contentType = multipartFile.getContentType();
            return uploadFile(bucket, content, contentType, name);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
