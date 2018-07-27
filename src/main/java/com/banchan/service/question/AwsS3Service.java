package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;

public interface AwsS3Service {
    PutObjectResponse upload(final DetailType type, final MultipartFile multipartFile);
    List<PutObjectResponse> upload(final DetailType type, final MultipartFile[] multipartFiles);
}
