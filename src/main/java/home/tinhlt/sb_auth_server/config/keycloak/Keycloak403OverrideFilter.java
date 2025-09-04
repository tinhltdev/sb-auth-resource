package home.tinhlt.sb_auth_server.config.keycloak;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import home.tinhlt.sb_auth_server.payloads.response.ApiResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Keycloak403OverrideFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	ContentCachingResponseWrapper  responseWrapper = new ContentCachingResponseWrapper ((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);

        int status = responseWrapper.getStatus();

        if (status == HttpServletResponse.SC_FORBIDDEN) {
            HttpServletResponse originalResponse = (HttpServletResponse) response;
            originalResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            originalResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ApiResponse customError = ApiResponse.builder()
                    .code(HttpServletResponse.SC_FORBIDDEN)
                    .message("Access denied: not_authorized")
                    .build();

            new ObjectMapper().writeValue(originalResponse.getOutputStream(), customError);
            originalResponse.flushBuffer();
        } else {
            responseWrapper.copyBodyToResponse();
        }
    }
}
