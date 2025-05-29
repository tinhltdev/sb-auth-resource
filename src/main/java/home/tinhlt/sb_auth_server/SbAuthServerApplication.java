package home.tinhlt.sb_auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class SbAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbAuthServerApplication.class, args);
	}
}
