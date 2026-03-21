package com.phope.realcalloutbackend.aws.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlResponse {

    private String uploadUrl;
    private String objectKey;
}
