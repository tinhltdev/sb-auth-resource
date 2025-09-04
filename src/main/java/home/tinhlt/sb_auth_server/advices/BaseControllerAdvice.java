package home.tinhlt.sb_auth_server.advices;

import java.nio.file.AccessDeniedException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import home.tinhlt.sb_auth_server.exceptions.AuthenticationException;
import home.tinhlt.sb_auth_server.payloads.response.ErrorApiResponse;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class BaseControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) throws InterruptedException {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		Map<String, String> errMessages = new HashMap<String, String>();
		for (FieldError error : fieldErrors) {
			errMessages.put(error.getField(), error.getDefaultMessage());
		}
		errMessages = errMessages.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		ErrorApiResponse response = ErrorApiResponse.builder().code(HttpStatus.BAD_REQUEST.value())
				.message("the request is invalid").data(errMessages).build();
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler({AuthorizationDeniedException.class})
	public ResponseEntity<?> handleAccessDeniedException(Exception ex) {
		ErrorApiResponse response = ErrorApiResponse.builder().code(HttpStatus.FORBIDDEN.value()).message(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleOAuthException(AuthenticationException ex) {
		ErrorApiResponse response = ErrorApiResponse.builder().code(ex.getCode()).message(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorApiResponse> handleAllExceptions(Exception ex) {
		log.error("Unhandled exception occurred", ex);

		ErrorApiResponse response = ErrorApiResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("An unexpected error occurred")
				.data(Map.of("exception", ex.getClass().getSimpleName(), "message", ex.getMessage())).build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
