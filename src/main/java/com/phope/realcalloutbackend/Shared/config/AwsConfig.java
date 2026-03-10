package com.phope.realcalloutbackend.Shared.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.plaf.synth.Region;

@Configuration
public class AwsConfig {

    @Value("${aws.region}")
    private String region;


    @Bean
    public  S3CLient s3CLient(){
        return  S3Client.builder()
                .region(Region.of(region))
                .build();

    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SesV2Client sesClient() {
        return SesV2Client.builder()
                .region(Region.of(region))
                .build();
    }

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.of(region))
                .build();
    }
}
