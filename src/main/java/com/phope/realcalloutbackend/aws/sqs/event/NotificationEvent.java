package com.phope.realcalloutbackend.aws.sqs.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    private String recipientEmail;
    private String subject;
    private String body;
}
