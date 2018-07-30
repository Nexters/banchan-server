package com.banchan.service.question;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface AwsS3Service {
    public PutObjectResponse upload(final String key, final MultipartFile file);
    public PutObjectResponse upload(final String key, final byte[] file);
}
