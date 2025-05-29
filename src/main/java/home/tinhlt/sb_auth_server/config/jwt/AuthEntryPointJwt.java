package home.tinhlt.sb_auth_server.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import home.tinhlt.sb_auth_server.payloads.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	 @Override
	  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	      throws IOException, ServletException {
	    log.error("authentication error: {}", authException.getMessage());

	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//	    final Map<String, Object> body = new HashMap<>();
//	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//	    body.put("error", "Unauthorized");
//	    body.put("message", authException.getMessage());
//	    body.put("path", request.getServletPath());
	    ApiResponse basicResponse = ApiResponse.builder().status(HttpServletResponse.SC_UNAUTHORIZED).message(authException.getMessage()).build();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), basicResponse);
	  }

}
