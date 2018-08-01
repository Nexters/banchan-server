package com.banchan.service.question;

import com.banchan.model.domain.question.DetailType;
import com.banchan.model.response.UploadResponse;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.RequestCharged;

public class ImageUploader {
    @Autowired
    private S3Client s3Client;

    @Autowired private Tika tika;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private static final String root = "img/";
    private static final int MAX_SIZE = 1024 * 1024 * 10; // 10MB

    public UploadResponse upload(int questionsId, DetailType type, byte[] file) {
        if(file == null || file.length == 0)
            return UploadResponse.fail(UploadResponse.Reason.NO_FILE);

        if(file.length > MAX_SIZE)
            return UploadResponse.fail(UploadResponse.Reason.OUT_OF_SIZE);

        String mimeType = tika.detect(file);

        return upload(this.key(questionsId, type, ".jpg"), file);
    }

    private UploadResponse upload(String key, byte[] file){
        return s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(root + key)
                        .build(),
                RequestBody.fromBytes(file))
                .requestCharged() == RequestCharged.REQUESTER ?
                UploadResponse.success() : UploadResponse.fail(UploadResponse.Reason.UNKNOWN);
    }

    private String key(int questionId, DetailType type, String extension){
        return new StringBuilder(questionId).append(type.name())
                .append(".").append(extension).toString();
    }
}
