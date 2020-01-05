package tech.wetech.admin.controller;

import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;

/**
 * @author cjbi
 */
@RestController
public class LoginController {

    public Result login() {
        return Result.success();
    }

}
