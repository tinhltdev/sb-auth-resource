package home.tinhlt.sb_auth_server.exceptions;

public class AuthenticationException extends BaseException {

	public AuthenticationException(int code, String message) {
		super(code, message);
	}
}
