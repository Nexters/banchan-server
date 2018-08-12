package com.banchan.config.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsConfig {

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        return InstanceProfileCredentialsProvider.create();
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
}
