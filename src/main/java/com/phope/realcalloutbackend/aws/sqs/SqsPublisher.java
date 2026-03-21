package com.phope.realcalloutbackend.aws.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phope.realcalloutbackend.aws.sqs.event.IncidentSubmittedEvent;
import com.phope.realcalloutbackend.aws.sqs.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsPublisher {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.incident-submitted-queue}")
    private String incidentSubmittedQueue;

    @Value("${aws.sqs.notification-queue}")
    private String notificationQueue;

    public void publishIncidentSubmitted(IncidentSubmittedEvent event) {
        publish(incidentSubmittedQueue, event);
    }

    public void publishNotification(NotificationEvent event) {
        publish(notificationQueue, event);
    }

    private <T> void publish(String queueUrl, T event){


        try {
            String body = objectMapper.writeValueAsString(event);

            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(body)
                    .build());

            log.info("Published event to SQS queue: {}", queueUrl);

        } catch (Exception e) {
            log.error("Failed to publish to SQS queue: {}", queueUrl, e);
            throw new RuntimeException("Failed to publish to SQS", e);
        }
    }
}
