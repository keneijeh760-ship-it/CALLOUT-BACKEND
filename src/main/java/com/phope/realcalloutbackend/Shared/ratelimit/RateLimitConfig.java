package com.phope.realcalloutbackend.Shared.ratelimit;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RateLimitConfig {

    @Value("${callout.rate-limit.submissions-per-hour:5}")
    private int submissionsPerHour;

    @Value("${callout.rate-limit.comments-per-hour:20}")
    private int commentsPerHour;


    @Value("${callout.rate-limit.upvotes-per-hour:50}")
    private int upvotesPerHour;


    @Value("${callout.rate-limit.status-updates-per-hour:100}")
    private int statusUpdatesPerHour;

}
