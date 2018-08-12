package com.banchan.config.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsConfig {

    @Value("${aws.s3.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.s3.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        return StaticCredentialsProvider.create(AwsCredentials.create(accessKeyId, secretAccessKey));
    }

    @Bean
    public S3Client s3Client(@Autowired AwsCredentialsProvider awsCredentialsProvider){
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of(region))
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(@Autowired AwsCredentialsProvider awsCredentialsProvider){
        return S3AsyncClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of(region))
                .build();
    }
}
