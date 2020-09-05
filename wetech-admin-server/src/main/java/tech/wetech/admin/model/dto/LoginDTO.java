package tech.wetech.admin.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    @Length(min = 6, max = 12)
    private String password;
}
