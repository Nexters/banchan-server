package com.banchan.config.question;

import com.banchan.service.question.AwsS3Service;
import com.banchan.service.question.AwsS3ServiceDeco;
import com.banchan.service.question.AwsS3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsConfig {

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        return EnvironmentVariableCredentialsProvider.create();
    }

    @Bean
    public S3Client s3Client(@Autowired AwsCredentialsProvider awsCredentialsProvider){
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(@Autowired AwsCredentialsProvider awsCredentialsProvider){
        return S3AsyncClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public AwsS3Service awsS3Service(@Autowired AwsS3ServiceImpl awsS3ServiceImpl){
        AwsS3ServiceDeco awsS3ServiceDeco = new AwsS3ServiceDeco();
        awsS3ServiceDeco.setAwsS3Service(awsS3ServiceImpl);

        return awsS3ServiceDeco;
    }

    @Bean
    public AwsS3ServiceImpl awsS3ServiceImpl(){
        return new AwsS3ServiceImpl();
    }
}
