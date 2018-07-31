package com.banchan.service.question;

import com.banchan.domain.question.DetailType;
import com.banchan.response.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageUploader {

    @Autowired private AwsS3Service awsS3Service;

    public ImageUploadResponse upload(int questionId, DetailType type, byte[] file){
        return null;
    }

    private String key(int questionId, DetailType type, String extension){
        return new StringBuilder(questionId).append(type.name())
                .append(".").append(extension).toString();
    }
}
