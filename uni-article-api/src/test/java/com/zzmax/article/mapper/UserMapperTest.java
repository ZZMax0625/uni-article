package com.zzmax.article.mapper;

import com.zzmax.article.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void insert() throws Exception{
        User user = User.builder()
                .phone("15162310144")
                .password("4295027270a60d246da869a9559d73d8") //000625 md5加密后
                .nickname("麦克斯Max")
                .avatar("http://iph.href.lu/100x100")
                .gender(0)
                .birthday(LocalDate.now())
                .address("江苏南京")
                .createTime(new Date())
                .build();
        userMapper.insert(user);
    }

    @Test
    void findUserByPhone() throws Exception{
        User user = userMapper.findUserByPhone("15162310144");
        assertNotNull(user);
        log.info(String.valueOf(user));
    }

}