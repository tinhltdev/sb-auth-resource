package home.tinhlt.sb_auth_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	@GetMapping
	public Object getInfo() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/admin")
	@PreAuthorize(value = "hasAuthority('EXCLUSIVE_READING')")
//	@Secured(value = {"ROLE_ADMIN"})
	public Object exclusiveReading() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
