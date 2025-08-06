package com.viet.blog_api.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.viet.blog_api.constant.ExecptionCode;
import com.viet.blog_api.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private void writeErrorResponse(HttpServletResponse response, String message, ExecptionCode code)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format("""
                    {
                        "message": "%s",
                        "code": "%s",
                        "timestamp": "%s"
                    }
                """, message, code, java.time.LocalDateTime.now());

        response.getWriter().write(json);
    }

    private String extractAccessToken(@NonNull HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = extractAccessToken(request);
        if (accessToken != null) {
            try {
                Authentication authentication = jwtUtil.extractAuthentication(accessToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException ex) {
                writeErrorResponse(response, "Expired Jwt", ExecptionCode.EXPIRED_JWT);
                return;
            } catch (JwtException ex) {
                writeErrorResponse(response, "Invalid Jwt", ExecptionCode.INVALID_JWT);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/auth");
    }

}
