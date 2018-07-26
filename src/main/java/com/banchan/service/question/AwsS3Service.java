package com.banchan.service.question;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;

public interface AwsS3Service {
    PutObjectResponse upload(final MultipartFile multipartFile);
    List<PutObjectResponse> upload(MultipartFile[] multipartFiles);
}
