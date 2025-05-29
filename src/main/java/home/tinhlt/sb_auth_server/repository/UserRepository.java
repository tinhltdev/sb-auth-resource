package home.tinhlt.sb_auth_server.repository;

import org.springframework.data.repository.CrudRepository;

import home.tinhlt.sb_auth_server.model.User;

public interface UserRepository extends CrudRepository<User, String>{
	public User findByUsername(String username);
}
