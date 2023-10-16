package com.vkls.wisdom.vod.controller;

import com.vkls.wisdom.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/admin/vod/user",method={RequestMethod.POST,RequestMethod.GET})
//@CrossOrigin(allowCredentials = "true")
@Api(tags="登录管理接口")
public class LoginController {
    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public Result login() {
        
        Map<String, Object> map = new HashMap<>();
        
        map.put("token","admin");
        
        return Result.ok(map);
    }
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public Result info() {
        
        Map<String, Object> map = new HashMap<>();
        
        map.put("roles","[admin]");
        
        map.put("name","admin");
        
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}