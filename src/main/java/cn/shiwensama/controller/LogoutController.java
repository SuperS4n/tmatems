package cn.shiwensama.controller;

import cn.shiwensama.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shiwensama
 * @create: 2020-04-03
 * @description:
 **/
@RestController
public class LogoutController {

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Result<Object> toLogin() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return new Result<>("退出成功");
    }
}
