package com.example.easychat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (!req.getServletPath().equals("/api/user/login") && !req.getServletPath().equals("/api/user/register")) {

            String authorHeader = req.getHeader(AUTHORIZATION);
            String bearer = "Bearer ";

            if (authorHeader != null && authorHeader.startsWith(bearer)) {
                try {
                    String token = authorHeader.substring(bearer.length());
                    Claims claims = Jwts.parser().setSigningKey("peko")
                            .parseClaimsJws(token).getBody();

                    System.out.println("JWT payload:" + claims.toString());

                    chain.doFilter(req, res);

                } catch (Exception e) {
                    System.err.println("Error : " + e);
                    res.setStatus(FORBIDDEN.value());

                    Map<String, String> err = new HashMap<>();
                    err.put("jwt_error", e.getMessage());
                    res.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(res.getOutputStream(), err);
                }
            } else {
                res.setStatus(UNAUTHORIZED.value());
            }
        } else {
            chain.doFilter(req, res);
        }

    }

}