package home.tinhlt.sb_auth_server.payloads.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse {
	private int status;
	private String message;
}
