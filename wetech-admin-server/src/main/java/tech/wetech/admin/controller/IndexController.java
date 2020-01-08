package tech.wetech.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author cjbi
 */
@Slf4j
@Controller
public class IndexController {

//    @GetMapping("/login")
//    public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.sendRedirect(request.getContextPath() + "/index.html");
//    }

//    @RequestMapping("/login")
//    @ResponseBody
//    public Result login() {
//        return Result.success();
//    }

}
