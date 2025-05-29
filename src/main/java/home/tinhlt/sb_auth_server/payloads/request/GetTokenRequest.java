package home.tinhlt.sb_auth_server.payloads.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTokenRequest {
	private String username;
	private String password;
}
