package com.banchan.service.question;

import com.banchan.domain.question.Reason;
import com.banchan.domain.upload.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public class AwsS3ServiceDeco implements AwsS3Service {

    private AwsS3Service awsS3Service;

    public void setAwsS3Service(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @Override
    public UploadResponse upload(String key, MultipartFile file) {
        if(file == null || file.getSize() == 0)
            return UploadResponse.fail().reason(Reason.NO_FILE);

        if(file.getSize() > MAX_SIZE)
            return UploadResponse.fail().reason(Reason.OUT_OF_SIZE);

        return upload(key, file);
    }

    @Override
    public UploadResponse upload(String key, byte[] file) {
        if(file == null || file.length == 0)
            return UploadResponse.fail().reason(Reason.NO_FILE);

        if(file.length > MAX_SIZE)
            return UploadResponse.fail().reason(Reason.OUT_OF_SIZE);

        return upload(key, file);
    }
}
