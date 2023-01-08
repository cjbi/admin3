package tech.wetech.admin3.service.dto;

import tech.wetech.admin3.model.UserCredential;

import java.io.Serializable;
import java.util.Set;

/**
 * @author cjbi
 */
public record UserInfoDTO(String token, Long userId, String username, String fullName, String avatar,
                          Credential credential, Set<String> permissions) implements Serializable {

    public record Credential(String identifier, UserCredential.IdentityType type) {
    }

}
