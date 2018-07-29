package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.Request;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AwsS3ServiceImpl implements AwsS3Service{

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private static final String dir = "/img/";

    public PutObjectResponse upload(final String key, final RequestBody requestBody) {

    }

    public PutObjectResponse upload(final String key, final MultipartFile file) {

        try {
            return s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(dir + key)
                            .build(),
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PutObjectResponse upload(final String key, final byte[] file) {

        return s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(dir + key)
                        .build(),
                RequestBody.fromBytes(file));
    }

    public List<PutObjectResponse> upload(final DetailType type, MultipartFile[] multipartFiles) {
        final List<PutObjectResponse> putObjectResponseList = new ArrayList<>();

        /*
        Arrays.stream(multipartFiles)
                .forEach((multipartFile ->
                        putObjectResponseList.add(upload(multipartFile))));
        */
        return putObjectResponseList;
    }
}
