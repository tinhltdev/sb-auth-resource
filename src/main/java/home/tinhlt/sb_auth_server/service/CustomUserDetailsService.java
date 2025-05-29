package home.tinhlt.sb_auth_server.service;

public interface CustomUserDetailsService {
	public Object generateToken(String username, String password);
}
