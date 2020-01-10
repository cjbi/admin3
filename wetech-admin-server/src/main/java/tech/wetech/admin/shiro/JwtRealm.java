package tech.wetech.admin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tech.wetech.admin.service.UserService;

@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = principals.toString();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.queryRoles(username));
        authorizationInfo.setStringPermissions(userService.queryPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String credentials = (String) token.getCredentials();
        String username = null;
        try {
            boolean verify = JwtUtil.verify(credentials);
            if (!verify) {
                throw new AuthenticationException("Token校验不正确");
            }
            username = JwtUtil.getClaim(credentials, JwtUtil.ACCOUNT);
        } catch (Exception e) {
            log.error("Token校验不正确：", e);
            throw new AuthenticationException(e.getMessage());
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，不设置则使用默认的SimpleCredentialsMatcher
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                credentials, //凭证
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
