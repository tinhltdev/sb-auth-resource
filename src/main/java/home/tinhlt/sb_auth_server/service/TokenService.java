package home.tinhlt.sb_auth_server.service;

public interface TokenService {
	public void storeTokens(String userId, String accessToken, String refreshToken);

	public String getAccessToken(String userId);

	public String getRefreshToken(String userId);

	public void deleteTokens(String userId);
}
