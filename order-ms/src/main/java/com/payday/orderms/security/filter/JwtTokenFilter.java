package com.payday.orderms.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payday.common.exception.BadRequestException;
import com.payday.common.exception.UnauthorizedException;
import com.payday.common.exception.constants.HttpResponseConstants;
import com.payday.orderms.security.service.UserService;
import com.payday.orderms.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {
        try {
        final String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            final String token = header.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                Claims claims = jwtUtil.getClaimsFromToken(token);

                String username = claims.getSubject();
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    } catch (BadRequestException | UnauthorizedException e) {
        handleException(request,response, e.getHttpStatus(), e.getMessage(),e.getExceptionKey());
    }
    }


    private void handleException(HttpServletRequest request, HttpServletResponse response,
                                 HttpStatus status, String message, String exceptionKey) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        Map<String, Object> errorObject = Map.of(
                HttpResponseConstants.STATUS, status.value(),
                HttpResponseConstants.ERROR, status.getReasonPhrase(),
                HttpResponseConstants.MESSAGE, message,
                HttpResponseConstants.ERRORS, Collections.EMPTY_LIST,
                HttpResponseConstants.KEY, "ORDER" + exceptionKey,
                HttpResponseConstants.PATH, webRequest.getRequest().getRequestURI()
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorObject));
    }
}
