package com.banchan.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public PutObjectResponse upload(final MultipartFile multipartFile) {

        try {
            return s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(multipartFile.getOriginalFilename())
                            .build(),
                    RequestBody.fromInputStream(
                            multipartFile
                                    .getInputStream(),
                            multipartFile
                                    .getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PutObjectResponse> upload(MultipartFile[] multipartFiles) {
        final List<PutObjectResponse> putObjectResponseList = new ArrayList<>();

        Arrays.stream(multipartFiles)
                .forEach((multipartFile ->
                        putObjectResponseList.add(upload(multipartFile))));

        return putObjectResponseList;
    }
}
