package home.tinhlt.sb_auth_server.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
}

