package com.phope.realcalloutbackend.Shared.config.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            var authenticate = SecurityContextHolder.getContext().getAuthentication();

            if (authenticate != null && authenticate.getPrincipal() instanceof  Jwt jwt){
                String orgIdClaim = jwt.getClaimsAsString(orgIdClaim);
            } else {
                logger.warn("Valid JWT has no org_id claim for subject: {}", jwt.getSubject());
            }
        }

        filterChain.doFilter(request, response);

    } finally {
        TenantContext.clear();
    }
}
