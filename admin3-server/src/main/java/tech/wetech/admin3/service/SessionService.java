package tech.wetech.admin3.service;

import org.springframework.stereotype.Service;
import tech.wetech.admin3.service.dto.UserInfoDTO;

/**
 * @author cjbi
 */
@Service
public interface SessionService {

    UserInfoDTO login(String username, String password);

    void logout(String token);

    boolean isLogin(String token);

    UserInfoDTO getLoginUserInfo(String token);

}
