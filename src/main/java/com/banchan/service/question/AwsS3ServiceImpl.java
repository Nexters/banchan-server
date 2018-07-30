package com.banchan.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

public class AwsS3ServiceImpl implements AwsS3Service{

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private String root = "img/";

    private PutObjectResponse putObject(final String key, RequestBody requestBody){
        return s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(root + key)
                        .build(),
                requestBody);
    }

    public PutObjectResponse upload(final String key, final MultipartFile file) {

        try {
            return this.putObject(key, RequestBody.fromInputStream(
                    file.getInputStream(),
                    file.getSize()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PutObjectResponse upload(final String key, final byte[] file) {

        return this.putObject(key, RequestBody.fromBytes(file));
    }

}
