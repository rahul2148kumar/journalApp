package com.rahul.journal_app.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
@WebFilter("/*") // Applies to all requests
public class GlobalRequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String httpMethod = httpRequest.getMethod();
        String requestUri = httpRequest.getRequestURI();

        log.info("{} : Path={}", httpMethod, requestUri);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
