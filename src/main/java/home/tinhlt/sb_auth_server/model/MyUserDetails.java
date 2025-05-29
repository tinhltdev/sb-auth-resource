package home.tinhlt.sb_auth_server.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUserDetails implements UserDetails, CredentialsContainer {

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

}