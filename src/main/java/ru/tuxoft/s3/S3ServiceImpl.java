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
import ru.tuxoft.book.domain.BookVO;
import ru.tuxoft.content.domain.PromoPictureVO;
import ru.tuxoft.content.domain.repository.PromoPictureRepository;
import ru.tuxoft.s3.domain.FileVO;
import ru.tuxoft.s3.domain.S3InitVO;
import ru.tuxoft.book.domain.repository.BookRepository;
import ru.tuxoft.s3.domain.repository.FileRepository;
import ru.tuxoft.s3.domain.repository.S3InitRepository;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

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

    @Autowired
    private S3InitRepository s3InitRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PromoPictureRepository promoPictureRepository;

    @PostConstruct
    public void initialBoot() {
        Optional<S3InitVO> s3InitVOOptional = s3InitRepository.findById(1L);
        if (s3InitVOOptional.isPresent() && !s3InitVOOptional.get().getInit()) {
            coverBooksInitial();
            promoPicturesInitial();
            notCoverImageInitial();
            S3InitVO s3InitVO = new S3InitVO();
            s3InitVO.setId(1L);
            s3InitVO.setInit(true);
            s3InitRepository.saveAndFlush(s3InitVO);
        }
    }

    private void notCoverImageInitial() {
        File f = new File("src/main/resources/s3/cover_books/notCoverImage.jpg");
        if (f.isFile()) {
            byte[] bytesArray = getFileAsBytesArray(f);
            FileVO file = new FileVO(f);
            file.setBucket(bucket);
            file.setKey("notCoverImage");
            uploadFile(file, bytesArray);
        }
    }




    private void promoPicturesInitial() {
        File dir = new File("src/main/resources/s3/promo_pictures");
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                Long promoPictureId = Long.valueOf(f.getName().substring(f.getName().lastIndexOf("_")+1,f.getName().lastIndexOf(".")));
                Optional<PromoPictureVO> promoPicture = promoPictureRepository.findById(promoPictureId);
                if (promoPicture.isPresent() && promoPicture.get().getPicture() == null) {
                    byte[] bytesArray = getFileAsBytesArray(f);
                    FileVO file = new FileVO(f);
                    file.setBucket(bucket);
                    file.setKey(UUID.randomUUID().toString());
                    uploadFile(file, bytesArray);
                    fileRepository.saveAndFlush(file);
                    PromoPictureVO promoPictureVO = promoPicture.get();
                    promoPictureVO.setPicture(file);
                    promoPictureRepository.saveAndFlush(promoPictureVO);
                }
            }
        }

    }

    private void coverBooksInitial() {
        File dir = new File("src/main/resources/s3/cover_books");
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                Long book_id;
                try {
                    book_id = Long.valueOf(f.getName().substring(f.getName().lastIndexOf("_") + 1, f.getName().lastIndexOf(".")));
                } catch (NumberFormatException e) {
                    continue;
                }
                Optional<BookVO> book = bookRepository.findById(book_id);
                if (book.isPresent() && book.get().getCoverFile() == null) {
                    byte[] bytesArray = getFileAsBytesArray(f);
                    FileVO file = new FileVO(f);
                    file.setBucket(bucket);
                    file.setKey(UUID.randomUUID().toString());
                    uploadFile(file, bytesArray);
                    fileRepository.saveAndFlush(file);
                    BookVO bookVO = book.get();
                    bookVO.setCoverFile(file);
                    bookRepository.saveAndFlush(bookVO);
                }
            }
        }
    }

    private byte[] getFileAsBytesArray(File f) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            bytesArray = new byte[(int) f.length()];
            fileInputStream = new FileInputStream(f);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesArray;
    }

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
    public String uploadFile(FileVO file, byte[] data) {
        return uploadFile(file.getKey(),file.getBucket(),data,file.getMimeType(),file.getName());
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
