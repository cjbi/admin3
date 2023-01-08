package tech.wetech.admin3.infra.service;

import org.springframework.stereotype.Service;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.exception.CommonResultStatus;
import tech.wetech.admin3.exception.UserException;
import tech.wetech.admin3.model.User;
import tech.wetech.admin3.model.UserCredential;
import tech.wetech.admin3.repository.UserCredentialRepository;
import tech.wetech.admin3.service.SessionService;
import tech.wetech.admin3.service.dto.UserInfoDTO;

import java.util.UUID;

import static tech.wetech.admin3.model.UserCredential.IdentityType.PASSWORD;

/**
 * @author cjbi
 */
@Service
public class DefaultSessionService implements SessionService {

    private final UserCredentialRepository userCredentialRepository;

    private final SessionManager sessionManager;

    public DefaultSessionService(UserCredentialRepository userCredentialRepository, SessionManager sessionManager) {
        this.userCredentialRepository = userCredentialRepository;
        this.sessionManager = sessionManager;
    }


    @Override
    public UserInfoDTO login(String username, String password) {
        UserCredential credential = userCredentialRepository.findCredential(username, PASSWORD)
                .orElseThrow(() -> new UserException(CommonResultStatus.UNAUTHORIZED, "密码不正确"));
        if (credential.doCredentialMatch(password)) {
            User user = credential.getUser();
            if (user.isLocked()) {
                throw new UserException(CommonResultStatus.UNAUTHORIZED, "用户已经停用，请与管理员联系");
            }
            String token = UUID.randomUUID().toString().replace("-", "");
            UserInfoDTO currentUser = new UserInfoDTO(token, user.getId(), user.getUsername(), user.getFullName(), user.getAvatar(), new UserInfoDTO.Credential(credential.getIdentifier(), credential.getIdentityType()),user.findPermissions());
            sessionManager.store(token, credential, currentUser);
            SessionItemHolder.setItem(Constants.SESSION_CURRENT_USER, currentUser);
            return currentUser;
        } else {
            throw new UserException(CommonResultStatus.UNAUTHORIZED, "密码不正确");
        }
    }

    @Override
    public void logout(String token) {
        sessionManager.invalidate(token);
    }

    @Override
    public boolean isLogin(String token) {
        return sessionManager.get(token) != null;
    }

    @Override
    public UserInfoDTO getLoginUserInfo(String token) {
        return (UserInfoDTO) sessionManager.get(token);
    }
}
