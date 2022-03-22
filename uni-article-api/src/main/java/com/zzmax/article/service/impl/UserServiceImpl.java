package com.zzmax.article.service.impl;


import com.zzmax.article.mapper.UserMapper;
import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.model.entity.User;
import com.zzmax.article.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(LoginDto localDat) {
        User user = getUser(localDat.getPhone());
        if (user != null){
            // 对客户端传递的密码进行加密后，再和数据库的密文比对
            return DigestUtils.md5Hex(localDat.getPassword()).equals(user.getPassword());
        }
        return false;
    }

    @Override
    public User getUser(String phone) {
        return userMapper.findUserByPhone(phone);
    }
}
