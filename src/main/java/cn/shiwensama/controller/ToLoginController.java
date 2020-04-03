package cn.shiwensama.controller;

import cn.shiwensama.enums.ResultEnum;
import cn.shiwensama.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwensama
 * @create: 2020-04-03
 * @description:
 **/
@RestController
public class ToLoginController {

    @RequestMapping("/tologin")
    public Result<Object> toLogin() {

        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("data","/login");
        return new Result<>(ResultEnum.ERROR.getCode(),"你没有登录",resultMap);
    }
}
