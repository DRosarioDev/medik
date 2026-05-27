package com.rosariodev.medik.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(1)
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
        
        logRequest(requestWrapper);
        
        long startTime = System.currentTimeMillis();
        
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logResponse(requestWrapper, responseWrapper, duration);
            responseWrapper.copyBodyToResponse();
        }
    }
    
    private void logRequest(HttpServletRequest request) {
        String queryString = request.getQueryString() != null ? request.getQueryString() : "N/A";
        
        // Costruisce la mappa delle intestazioni
        Map<String, String> headers = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                    name -> name,
                    name -> request.getHeader(name)
                ));
        
        log.trace("Ricevuta richiesta \n\t[{}] {}\n\tQuery String: {}\n\tIntestazioni: {}",
                request.getMethod(),
                request.getRequestURI(),
                queryString,
                headers);
    }
    
    private void logResponse(HttpServletRequest request, HttpServletResponse response, long duration) {
        log.trace("Generata risposta per la richiesta \n\t[{}] {} - Stato: {} - Durata: {}ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration);
    }
}