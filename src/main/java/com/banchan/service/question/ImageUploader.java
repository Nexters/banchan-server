package com.banchan.service.question;

import com.banchan.model.exception.ImageUploadException;
import com.google.common.base.Strings;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.stream.Stream;

@Service
public class ImageUploader {
    @Autowired
    private S3Client s3Client;

    @Autowired private Tika tika;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private static final String root = "img/";
    private static final int MAX_SIZE = 1024 * 1024 * 10; // 10MB

    public String upload(String key, byte[] file) {

        key +=  precondition(file);

        // 예외 확인 필요 NULL POINTER 같은 거
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(root + key)
                        .build(),
                RequestBody.fromBytes(file));

        return key;
    }

    public String precondition(byte[] file){
        if(file == null || file.length == 0 || file.length > MAX_SIZE)
            throw new ImageUploadException("이미지 업로드 실패");

        //Illegal state exception 발생 가능
        return Stream.of(tika.detect(file))
                .map(Strings::nullToEmpty)
                .filter(mimeType -> mimeType.startsWith("image/"))
                .map(mimeType -> mimeType.replaceAll("image/", "."))
                .findFirst()
                .orElseThrow(() -> new ImageUploadException("파일 형식이 맞지 않습니다"));
    }
}
