package com.post.blog.security;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiTokenFilter extends OncePerRequestFilter {

    private String apiToken;

    public ApiTokenFilter(String apiToken) {
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalArgumentException("API token cannot be null or empty");
        }
        this.apiToken = apiToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Only check non-GET requests
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                String token = bearerToken.substring(7);

                if (token.equals(apiToken)) {
                    // set anonymous authentication in SecurityContext, maybe not ideal solution
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken("apiUser", null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                    logger.warn("Unauthorized request from IP: " + request.getRemoteAddr());
                    return;
                }
            }
        }

        // Continue to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
