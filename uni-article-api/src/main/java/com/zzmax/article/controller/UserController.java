package com.zzmax.article.controller;


import com.zzmax.article.common.ResponseResult;
import com.zzmax.article.common.ResultCode;
import com.zzmax.article.model.dto.BindTelDto;
import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.model.dto.WxLoginDto;
import com.zzmax.article.model.entity.User;
import com.zzmax.article.service.RedisService;
import com.zzmax.article.service.UserService;
import com.zzmax.article.util.FileResource;
import com.zzmax.article.util.SmsUtil;
import com.zzmax.article.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Resource
    private SmsUtil smsUtil;

    @Resource
    private RedisService redisService;

    @Resource
    private UserService userService;
    @Resource
    private FileResource fileResource;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        log.info("loginDto:" + loginDto);
        boolean flag = userService.login(loginDto);
        if (flag){
            log.info("/login " + userService.getUser(loginDto.getPhone()));
            return ResponseResult.success(userService.getUser(loginDto.getPhone()));
        }
        else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }
    }

    @PostMapping("/login/sms")
    public ResponseResult smsLogin(@RequestBody LoginDto loginDto){
        log.info("loginDto:" + loginDto);
        boolean flag = userService.loginByCode(loginDto);
        if (flag){
            return ResponseResult.success(userService.getUser(loginDto.getPhone()));

        }
        else {
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
        }
    }

    @PostMapping(value = "/bind")
    public ResponseResult bindTel(@RequestBody BindTelDto bindTelDto){
        log.info("bindTel" + bindTelDto);
        User user = userService.findByOpenId(bindTelDto.getWxOpenId());

        user.setPhone(bindTelDto.getPhone());
        user = userService.updateUser(user);


        log.info("bindTel after " + user);
        return ResponseResult.success(user);

    }

    @PostMapping(value = "/login/wx")
    public ResponseResult wxLogin(@RequestBody WxLoginDto wxLoginDto) {
        log.info("wxLoginDto:" + wxLoginDto);
        User user = userService.wxLogin(wxLoginDto);
        if (user == null) {
            log.info("新用户");
            return ResponseResult.success(userService.findByOpenId(wxLoginDto.getWxOpenId()));
        }
        return ResponseResult.success(userService.findByOpenId(wxLoginDto.getWxOpenId()));
    }


    @PostMapping("/sms")
    public ResponseResult sendSms (@RequestParam String phone) {
        System.out.println(phone);
        String code = StringUtil.getVerifyCode();
        boolean flag = smsUtil.sendSms(phone,code);
        redisService.set(phone, code,  1L) ;
        if (true) {
            return ResponseResult.success(code);
        } else {
            return ResponseResult.failure (ResultCode.SMS_ERROR) ;
        }
    }

    @PostMapping(value = "/update")
    public User update(@RequestBody User user) {
        log.info("user:" + user);
        return userService.updateUser(user);
    }

    @PostMapping(value = "/upload")
    public ResponseResult uploadFile(MultipartFile file) {
        // 声明图片的地址路径，返回到前端
        String path = null;
        // 判断文件不能为空
        if (file != null) {
            // 获得文件上传的名称
            String fileName = file.getOriginalFilename();
            log.info(fileName);
            //调用上传服务，得到上传后的新文件名
            path = userService.uploadFile(file);
        }
        if (StringUtils.isNotBlank(path)) {
            //拼接上服务器地址前缀，得到最终返回给前端的url
            path = fileResource.getOssHost() + path;
            log.info(path);
        }
        return ResponseResult.success(path);
    }
}
