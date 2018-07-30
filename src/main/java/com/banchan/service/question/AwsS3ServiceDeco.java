package com.banchan.service.question;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class AwsS3ServiceDeco implements AwsS3Service {

    private AwsS3Service awsS3Service;

    public void setAwsS3Service(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @Override
    public PutObjectResponse upload(String key, MultipartFile file) {
        if(file != null && file.getSize() != 0)
            return awsS3Service.upload(key, file);

        return null;
    }

    @Override
    public PutObjectResponse upload(String key, byte[] file) {
        if(file != null && file.length != 0)
            return awsS3Service.upload(key, file);

        return null;
    }
}
