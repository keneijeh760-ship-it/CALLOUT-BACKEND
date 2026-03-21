package com.phope.realcalloutbackend.aws.ses;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Service
@RequiredArgsConstructor
public class SesEmailService {

    private final SesV2Client sesClient;

    @Value("${aws.ses.from-email}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .fromEmailAddress(fromEmail)
                .destination(Destination.builder()
                        .toAddresses(toEmail)
                        .build())
                .content(EmailContent.builder()
                        .simple(Message.builder()
                                .subject(Content.builder()
                                        .data(subject)
                                        .build())
                                .body(Body.builder()
                                        .text(Content.builder()
                                                .data(body)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        sesClient.sendEmail(request);
    }
}
