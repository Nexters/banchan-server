package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AwsS3ServiceImpl implements AwsS3Service{

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public PutObjectResponse upload(final DetailType type, final MultipartFile multipartFile) {

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

    public List<PutObjectResponse> upload(final Map<String, MultipartFile> images) {
        final List<PutObjectResponse> putObjectResponseList = new ArrayList<>();

        // 입력된 key 이름이랑 서버의 key 이름이랑 다를 수 있음 (예외 필요)
        images.keySet().stream()
                .map((key) -> DetailType.valueOf(key))
                .forEach((type ->
                        putObjectResponseList.add(upload(type, images.get(type.name())))));

        return putObjectResponseList;
    }
}
