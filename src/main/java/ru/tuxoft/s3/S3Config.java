package ru.tuxoft.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.amazonaws.SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY;

@Configuration
public class S3Config {

    @Value("${s3.url}")
    private String s3Url;

    @Value("${s3.access_key_id}")
    private String s3Id;

    @Value("${s3.secret_access_key}")
    private String s3Key;

    @Value("${s3.bucket}")
    private String bucket;

    @Bean
    public AmazonS3 s3client() {
        if (s3Url == null || s3Url.isEmpty()) {
            return null;
        }
        System.setProperty(DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3Id, s3Key);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");
        AwsClientBuilder.EndpointConfiguration ec = new AwsClientBuilder.EndpointConfiguration(s3Url, Regions.DEFAULT_REGION.getName());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withClientConfiguration(clientConfig)
                .withEndpointConfiguration(ec)
                .build();

        if (bucket != null && !bucket.isEmpty()) {
            List<Bucket> buckets = s3Client.listBuckets();
            boolean found = false;
            for (Bucket b : buckets) {
                if (b.getName().equals(bucket)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                s3Client.createBucket(bucket);
            }
        }

        return s3Client;
    }
}
