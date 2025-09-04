package home.tinhlt.sb_auth_server.controller;

import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import home.tinhlt.sb_auth_server.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8082" }, allowCredentials = "true")
public class AuthController {

	@Autowired
	private WebClient webclient;

	@Value("${keycloak.token-uri}")
	private String tokenUri;

	@Value("${keycloak.client-id}")
	private String clientId;

	@Value("${keycloak.client-secret}")
	private String clientSecret;

	private Function<ClientResponse, Mono<? extends Throwable>> handleOAuthError() {
		return clientResponse -> clientResponse.bodyToMono(Map.class).flatMap(errorBody -> {
			Object desc = errorBody.get("error_description");
			String message = desc != null ? desc.toString() : "Unknown error";
			return Mono.error(new AuthenticationException(4011, message));
		});
	}
	
	//TODO: api register an user
	@PostMapping("/register")
	public ResponseEntity<?> register(){
		return ResponseEntity.ok(200);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
		String username = credentials.get("username");
		String password = credentials.get("password");

		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "password");
		form.add("client_id", clientId);
		form.add("client_secret", clientSecret);
		form.add("username", username);
		form.add("password", password);

		ResponseEntity<Map> response = webclient.post().uri(tokenUri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(form)).retrieve()
				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), handleOAuthError())
				.toEntity(Map.class).block();

		// Save tokens in server session (backend)
		session.setAttribute("access_token", response.getBody().get("access_token"));
		session.setAttribute("refresh_token", response.getBody().get("refresh_token"));

		// Return access token to frontend
		return ResponseEntity.ok(response.getBody());
	}

	@GetMapping("/status")
	public ResponseEntity<?> silentLogin(HttpSession session) {
		String refreshToken = (String) session.getAttribute("refresh_token");
		if (refreshToken == null) {
			throw new AuthenticationException(4012, "No active session");
		}

		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("grant_type", "refresh_token");
		form.add("client_id", clientId);
		form.add("client_secret", clientSecret);
		form.add("refresh_token", refreshToken);

		ResponseEntity<Map> response = webclient.post().uri(tokenUri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(form)).retrieve()
				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), handleOAuthError())
				.toEntity(Map.class).block();

		// Update tokens in session
		session.setAttribute("access_token", response.getBody().get("access_token"));
		session.setAttribute("refresh_token", response.getBody().get("refresh_token"));

		return ResponseEntity.ok(response.getBody());
	}
}
