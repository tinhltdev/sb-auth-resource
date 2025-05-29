package home.tinhlt.sb_auth_server.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import home.tinhlt.sb_auth_server.config.jwt.AuthEntryPointJwt;
import home.tinhlt.sb_auth_server.config.jwt.AuthTokenFilter;
import home.tinhlt.sb_auth_server.config.jwt.CustomAccessDeniedHandler;
import home.tinhlt.sb_auth_server.service.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	@Order(1)
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/**")
				.authorizeHttpRequests(authz -> authz.requestMatchers("/api/auth/token", "/api/auth/signup").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(customAccessDeniedHandler))
				.csrf(csrf -> csrf.disable());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain formSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/login", "/error", "/default-ui.css", "/.well-known/appspecific/com.chrome.devtools.json", "/css/**", "/js/**")
				.permitAll().anyRequest().authenticated())
				.formLogin(form -> form.defaultSuccessUrl("/", false))
				.logout((logout) -> logout.permitAll());
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
}
