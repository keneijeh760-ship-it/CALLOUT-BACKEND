package com.phope.realcalloutbackend.Shared.config;

import com.phope.realcalloutbackend.Shared.config.tenant.TenantFilter;
import com.phope.realcalloutbackend.Shared.ratelimit.RateLimitConfig;
import lombok.RequiredArgsConstructor;
import com.phope.realcalloutbackend.Shared.ratelimit.RateLimiterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TenantFilter tenantFilter;
    private final RateLimiterFilter rateLimitFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "incident/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/feed/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "categories/**").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt-> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )

                .addFilterAfter(tenantFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(rateLimitFilter, TenantFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("role");
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    @Bean
    public RateLimiterFilter rateLimitFilter(RedisTemplate<String, Object> redisTemplate,
                                           RateLimitConfig rateLimitConfig) {
        return new RateLimiterFilter(redisTemplate, rateLimitConfig);
    }


}
