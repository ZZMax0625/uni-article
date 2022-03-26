package com.zzmax.article.service;

import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.model.dto.WxLoginDto;
import com.zzmax.article.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    /**
     * @param loginDto 登陆dto对象
     * @return 登陆结果
     */
    boolean login(LoginDto loginDto);

    /**
     * @param phone 手机号
     * @return user对象
     */
    User getUser(String phone);

    public boolean loginByCode(LoginDto loginDto);

    /**
     * 微信登录
     */
    User wxLogin(WxLoginDto wxLoginDto);

    /**
     * 根据微信openId查询
     */
    User findByOpenId(String wxOpenId);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return User
     */
    User updateUser(User user);

    /**
     * 上传文件到OSS
     *
     * @param file 文件对象
     * @return 上传后的url
     */
    String uploadFile(MultipartFile file);
}
