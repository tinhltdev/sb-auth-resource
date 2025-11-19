package home.tinhlt.sb_auth_server.utils;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class TokenUtils {
	
	public static String getAuditor() {
		Jwt jwt = getJwt();
		if (jwt != null) {
			return jwt.getClaimAsString("preferred_username");
		}
		return "";
	}
	
	public static Jwt getJwt() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			if (auth instanceof JwtAuthenticationToken jwtAuth) {
				return jwtAuth.getToken();
			}
		}
		return null;
	}
	
	//used for job
	public static String getAuditor(Authentication auth) {
		Jwt jwt = getJwt(auth);
		if (jwt != null) {
			return jwt.getClaimAsString("preferred_username");
		}
		return "";
	}
	
	public static Jwt getJwt(Authentication auth) {
		if (auth != null && auth.isAuthenticated()) {
			if (auth instanceof JwtAuthenticationToken jwtAuth) {
				return jwtAuth.getToken();
			}
		}
		return null;
	}
	
	public static String getSub() {
		Jwt jwt = getJwt();
		if (jwt != null) {
			return jwt.getSubject();
		}
		return "";

	}
}