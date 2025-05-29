package home.tinhlt.sb_auth_server.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import home.tinhlt.sb_auth_server.model.Permission;
import jakarta.persistence.NamedNativeQuery;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer>{
	
	@Query(nativeQuery = true, value = "SELECT p.* FROM permissions p INNER JOIN role_permission rp ON p.id = rp.permission_id"
			+ " INNER JOIN roles r on rp.role_id = r.id"
			+ " INNER JOIN user_role ur on r.id = ur.role_id AND ur.user_id = :userId")
	public List<Permission> findAllByUserId(@Param("userId")Integer userId);
}
