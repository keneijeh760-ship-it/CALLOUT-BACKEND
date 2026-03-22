package com.phope.realcalloutbackend.Shared.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RateLimitConfig rateLimitConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = jwt.getSubject();

        String method = request.getMethod();
        String path = request.getRequestURI();

        RateLimitRule rule = resolveRule(method, path);

        if (rule == null) {
            // No rate limit rule for this endpoint — allow through
            filterChain.doFilter(request, response);
            return;
        }

        String redisKey = "rate:" + rule.action() + ":" + userId;

        Long currentCount = redisTemplate.opsForValue().increment(redisKey);

        if (currentCount == 1) {

            redisTemplate.expire(redisKey, Duration.ofHours(1));
        }

        if (currentCount > rule.limit()) {
            log.warn("Rate limit exceeded for user: {} on action: {}",
                    userId, rule.action());

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("""
                    {
                        "success": false,
                        "message": "Rate limit exceeded. Please try again later."
                    }
                    """);
            return;

        }

        response.setHeader("X-RateLimit-Limit", String.valueOf(rule.limit()));
        response.setHeader("X-RateLimit-Remaining",
                String.valueOf(Math.max(0, rule.limit() - currentCount)));

        filterChain.doFilter(request, response);




    }

    private RateLimitRule resolveRule(String method, String path) {

        // POST /incidents — incident submission
        // Most strictly limited — prevents spam flooding
        if (HttpMethod.POST.matches(method) && path.contains("/incidents")
                && !path.contains("/comments") && !path.contains("/status")) {
            return new RateLimitRule("submit", rateLimitConfig.getSubmissionsPerHour());
        }

        // POST /incidents/{id}/comments — comment creation
        if (HttpMethod.POST.matches(method) && path.contains("/comments")) {
            return new RateLimitRule("comment", rateLimitConfig.getCommentsPerHour());
        }

        // POST /incidents/{id}/upvote — upvoting
        if (HttpMethod.POST.matches(method) && path.contains("/upvote")) {
            return new RateLimitRule("upvote", rateLimitConfig.getUpvotesPerHour());
        }

        // PATCH /incidents/{id}/status — status updates
        if (HttpMethod.PATCH.matches(method) && path.contains("/status")) {
            return new RateLimitRule("status", rateLimitConfig.getStatusUpdatesPerHour());
        }

        // No rule matched — no rate limiting for this endpoint
        return null;
    }

    private record RateLimitRule(String action, int limit) {}
}
