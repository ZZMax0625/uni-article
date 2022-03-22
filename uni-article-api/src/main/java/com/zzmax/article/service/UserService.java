package com.zzmax.article.service;

import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.model.entity.User;

import java.time.LocalDate;

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
}
