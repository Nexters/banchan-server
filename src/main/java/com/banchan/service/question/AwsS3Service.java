package com.banchan.service.question;

import com.banchan.domain.upload.UploadResponse;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface AwsS3Service {

    static final int MAX_SIZE = 1024 * 1024 * 10; // 10MB

    UploadResponse upload(final String key, final MultipartFile file);
    UploadResponse upload(final String key, final byte[] file);
}
