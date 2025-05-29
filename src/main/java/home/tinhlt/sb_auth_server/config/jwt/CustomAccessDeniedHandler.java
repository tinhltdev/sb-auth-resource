package home.tinhlt.sb_auth_server.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import home.tinhlt.sb_auth_server.payloads.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
	    log.error("Unauthorized error: {}", accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    ApiResponse basicResponse = ApiResponse.builder().status(HttpServletResponse.SC_FORBIDDEN).message(accessDeniedException.getMessage()).build();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), basicResponse);
    }
}
