package home.tinhlt.sb_auth_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;

@Entity(name = "permissions")
@Getter
public class Permission {
	@Id
	private Integer id;
	private String name;
}
