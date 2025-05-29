package home.tinhlt.sb_auth_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import home.tinhlt.sb_auth_server.config.jwt.JwtUtils;
import home.tinhlt.sb_auth_server.payloads.request.GetTokenRequest;
import home.tinhlt.sb_auth_server.payloads.response.ApiResponse;
import home.tinhlt.sb_auth_server.payloads.response.GetTokenResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/token")
	public ResponseEntity<?> getToken(@RequestBody GetTokenRequest getTokenRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					getTokenRequest.getUsername(), getTokenRequest.getPassword()));
			String accessToken = jwtUtils.generateJwtToken(authentication);
			return ResponseEntity.ok(GetTokenResponse.builder().status(HttpStatus.OK.value()).accessToken(accessToken).build());
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(ApiResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
		}
	}
	
	@GetMapping("/test")
	@PreAuthorize(value = "hasAuthority('EXCLUSIVE_READING')")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("OK");
	}
}
