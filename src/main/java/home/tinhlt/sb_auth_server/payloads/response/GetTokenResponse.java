package home.tinhlt.sb_auth_server.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetTokenResponse {
	private int status;
	private String accessToken;
}
