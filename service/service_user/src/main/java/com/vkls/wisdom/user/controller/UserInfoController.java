package com.vkls.wisdom.user.controller;


import com.vkls.wisdom.model.user.UserInfo;
import com.vkls.wisdom.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author vkls
 * @since 2022-12-09
 */
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userService;

    @ApiOperation(value = "获取")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}

