package home.tinhlt.sb_auth_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class CheckController {

	@GetMapping
	public String getOrder() {
		return "GET order";
	}
	
	
	@GetMapping("/get-by-name/{name}")
	public String getOrderByName(String name) {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		 System.out.println(authentication.getPrincipal());
		return "GET order by name";
	}
	
	@PostMapping("/get-by-name/{name}")
	public String postOrderByName(String name) {
		return "POST order by name";
	}
	
	@GetMapping("/{id}")
	public String getOrderById() {
		return "GET order";
	}
	
	@PostMapping
	public String createOrder() {
		return "Create order";
	}
	
	@GetMapping("/info-dma")
	public String checkInfoDomainA() {
		return "Info domain A";
	}
	@GetMapping("/info-dmb")
	public String checkInfoDomainB() {
		return "Info domain B";
	}
}
