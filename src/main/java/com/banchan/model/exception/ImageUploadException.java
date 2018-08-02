package com.banchan.model.exception;

import com.banchan.model.response.UploadResponse;

import java.util.Map;

public class ImageUploadException extends RuntimeException{

    private Map<Integer, UploadResponse> responses;

    public ImageUploadException(Map<Integer, UploadResponse> responses) {
        this.responses = responses;
    }
}
