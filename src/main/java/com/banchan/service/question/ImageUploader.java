package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.response.UploadResponse;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.RequestCharged;

@Service
public class ImageUploader {
    @Autowired
    private S3Client s3Client;

    @Autowired private Tika tika;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private static final String root = "img/";
    private static final int MAX_SIZE = 1024 * 1024 * 10; // 10MB

    public UploadResponse upload(String key, byte[] file) {
        if(file == null || file.length == 0)
            return UploadResponse.fail(UploadResponse.Reason.NO_FILE);

        if(file.length > MAX_SIZE)
            return UploadResponse.fail(UploadResponse.Reason.OUT_OF_SIZE);

        return s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(root + key)
                        .build(),
                RequestBody.fromBytes(file))
                .requestCharged() == RequestCharged.REQUESTER ?
                UploadResponse.success() : UploadResponse.fail(UploadResponse.Reason.UNKNOWN);
    }

}
