package tech.wetech.admin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author cjbi
 */
public class JwtToken implements AuthenticationToken {

    private final String credentials;

    public JwtToken(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException("Unsupported principal.");
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
