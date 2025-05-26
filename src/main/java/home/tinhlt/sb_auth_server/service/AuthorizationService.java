package home.tinhlt.sb_auth_server.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("authzService")
public class AuthorizationService {

    public boolean hasPermission(Authentication authentication, String resourceName, String scope) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Jwt jwt = token.getToken();

        Map<String, Object> authz = jwt.getClaim("authorization");

        if (authz != null) {
            List<Map<String, Object>> permissions = (List<Map<String, Object>>) authz.get("permissions");

            if (permissions != null) {
                for (Map<String, Object> permission : permissions) {
                    String resource = (String) permission.get("rsname");
                    List<String> scopes = (List<String>) permission.get("scopes");

                    if (resourceName.equals(resource) && scopes.contains(scope)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
