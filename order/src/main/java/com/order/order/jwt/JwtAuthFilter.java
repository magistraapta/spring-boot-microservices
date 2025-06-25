package com.order.order.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader("Authorization");
            log.info("Request URI: {}", request.getRequestURI());
            log.info("Authorization header: {}", jwtToken != null ? jwtToken.substring(0, Math.min(20, jwtToken.length())) + "..." : "null");
            
            if (StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7); // Remove "Bearer " prefix
                log.info("Processing JWT token: {}", jwtToken.substring(0, Math.min(20, jwtToken.length())) + "...");
                
                Claims claims = jwtUtil.getClaimsFromToken(jwtToken);
                String username = claims.getSubject();
                String issuer = claims.getIssuer();
                
                log.info("JWT claims - Subject (username): {}, Issuer: {}", username, issuer);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(issuer);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                log.info("Authentication set for user: {}", username);
            } else {
                log.warn("No valid Authorization header found. Token: {}", jwtToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage(), e);
        }
        filterChain.doFilter(request, response);
    }
}
