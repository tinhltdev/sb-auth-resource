package home.tinhlt.sb_auth_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name="roles")
@Getter
@Setter
public class Role {

	@Id
	private Integer id;
	private String name;
	private String code;
}
