package com.zzmax.article.controller;


import com.zzmax.article.common.ResponseResult;
import com.zzmax.article.common.ResultCode;
import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        log.info("loginDto:" + loginDto);
        boolean flag = userService.login(loginDto);
        if (flag){
            return ResponseResult.success(userService.getUser(loginDto.getPhone()));
        }
        else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }
    }
}
