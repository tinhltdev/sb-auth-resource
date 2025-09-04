package home.tinhlt.sb_auth_server.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class TokenServiceImpl implements TokenService {

	private static final long TOKEN_EXPIRATION_MINUTES = 60;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void storeTokens(String userId, String accessToken, String refreshToken) {
		redisTemplate.opsForHash().put(userId, "accessToken", accessToken);
		redisTemplate.opsForHash().put(userId, "refreshToken", refreshToken);
		redisTemplate.expire(userId, TOKEN_EXPIRATION_MINUTES, TimeUnit.MINUTES);
	}

	public String getAccessToken(String userId) {
		return (String) redisTemplate.opsForHash().get(userId, "accessToken");
	}

	public String getRefreshToken(String userId) {
		return (String) redisTemplate.opsForHash().get(userId, "refreshToken");
	}

	public void deleteTokens(String userId) {
		redisTemplate.delete(userId);
	}
}
