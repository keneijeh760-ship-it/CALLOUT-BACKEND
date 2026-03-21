package com.phope.realcalloutbackend.aws.s3;

import com.phope.realcalloutbackend.aws.s3.dto.PresignedUrlResponse;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.presigned-url-expiry-minutes}")
    private int expiryMinutes;

    public PresignedUrlResponse generateUploadUrl (String filename, String contentType){
        String objectKey = UUID.randomUUID() + "/" +  filename;

        PutObject
    }

}
