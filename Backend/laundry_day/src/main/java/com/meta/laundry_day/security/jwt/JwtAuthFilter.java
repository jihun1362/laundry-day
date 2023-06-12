package com.meta.laundry_day.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.meta.laundry_day.common.message.ErrorCode.TOKEN_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.USER_NOT_FOUND_ERROR;

@Slf4j
@RequiredArgsConstructor
public class  JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!jwtUtil.validateToken(token)) {
            jwtExceptionHandler(response, TOKEN_ERROR);
            return;
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);
        setAuthentication(response, info.getSubject());
        filterChain.doFilter(request, response);
    }
    public void setAuthentication(HttpServletResponse response,String email) {
        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = jwtUtil.createAuthentication(email);
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        } catch (UsernameNotFoundException e) {
            jwtExceptionHandler(response, USER_NOT_FOUND_ERROR);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatusCode());
        response.setContentType("application/json; charset=utf8");
        try {
            String json = new ObjectMapper().writeValueAsString(new ResponseDto<>(errorCode, errorCode));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}