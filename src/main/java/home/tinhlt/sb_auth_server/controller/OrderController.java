package home.tinhlt.sb_auth_server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@GetMapping
	public String getOrder() {
		return "GET order";
	}
	
	
	@GetMapping("/get-by-name/{name}")
	public String getOrderByName(String name) {
		return "GET order by name";
	}
	
	@GetMapping("/{id}")
	public String getOrderById() {
		return "GET order";
	}
	
	@PostMapping
	public String createOrder() {
		return "Create order";
	}
}
