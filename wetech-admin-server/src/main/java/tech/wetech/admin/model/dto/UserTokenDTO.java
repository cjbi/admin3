package tech.wetech.admin.model.dto;

import lombok.Data;

@Data
public class UserTokenDTO {

    private String username; //用户名
    private String token;
}
