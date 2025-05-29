package home.tinhlt.sb_auth_server.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import home.tinhlt.sb_auth_server.model.Permission;
import home.tinhlt.sb_auth_server.model.MyUserDetails;
import home.tinhlt.sb_auth_server.model.User;
import home.tinhlt.sb_auth_server.repository.PermissionRepository;
import home.tinhlt.sb_auth_server.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return new MyUserDetails();
		}
		Set<GrantedAuthority> authorities = permissionRepository.findAllByUserId(user.getId()).stream().map(permission -> new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return permission.getName().toUpperCase();
			}
		}).collect(Collectors.toSet());
		return MyUserDetails.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
	}

	@Override
	public Object generateToken(String username, String password) {
		return null;
	}

}
