package com.zzmax.article.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zzmax.article.mapper.UserMapper;
import com.zzmax.article.model.dto.LoginDto;
import com.zzmax.article.model.dto.WxLoginDto;
import com.zzmax.article.model.entity.User;
import com.zzmax.article.service.RedisService;
import com.zzmax.article.service.UserService;
import com.zzmax.article.util.AliyunResource;
import com.zzmax.article.util.FileResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private AliyunResource aliyunResource;
    @Resource
    private FileResource fileResource;

    @Override
    public boolean login(LoginDto localDat) {
        log.info("UserServiceImpl:login localDat:" + localDat);
        User user = getUser(localDat.getPhone());
        if (user != null) {
            // 对客户端传递的密码进行加密后，再和数据库的密文比对
            log.info(String.valueOf(DigestUtils.md5Hex(localDat.getPassword()).equals(user.getPassword())));
            return DigestUtils.md5Hex(localDat.getPassword()).equals(user.getPassword());
        }
        return false;
    }

    public boolean loginByCode(LoginDto loginDto) {
        boolean flag = redisService.existsKey(loginDto.getPhone());
        if (flag) {
            String saveCode = redisService.getValue(loginDto.getPhone(), String.class);
            if (saveCode.equalsIgnoreCase(loginDto.getCode())) {
                User user = getUser(loginDto.getPhone());
                if (user == null) {
                    User saveUser = User.builder()
                            .phone(loginDto.getPhone())
                            .nickname("新用户")
                            .avatar("/static/img/dog.png")
                            .createTime(new Date())
                            .build();
                    userMapper.insert(saveUser);
                }
                return true;
            }

        }
        return false;

    }

    @Override
    public User wxLogin(WxLoginDto wxLoginDto) {
        User user = findByOpenId(wxLoginDto.getWxOpenId());
        //新用户
        if (user == null) {
            user = User.builder()
                    .phone("")
                    .wxOpenId(wxLoginDto.getWxOpenId())
                    .nickname(wxLoginDto.getNickname())
                    .avatar(wxLoginDto.getAvatar())
                    .gender(wxLoginDto.getGender())
                    .address("江苏南京")
                    .banner("")
                    .createTime(new Date()).build();
            userMapper.insert(user);
        }
        //老用户
        return user;
    }

    @Override
    public User updateUser(User user) {
        //先根据手机号查出数据库原用户信息
        log.info("updateUser1" + String.valueOf(user));
        User savedUser;

        if (user.getPhone() != null){
            savedUser = findByOpenId(user.getWxOpenId());
        }
        else {
            savedUser = getUser(user.getPhone());
        }

        log.info("updateUser2" + String.valueOf(savedUser));
        //如果是修改密码的请求，需要将传来的密码加密
        if (user.getPassword() != null){
            if (!user.getPassword().equals(savedUser.getPassword())) {
                savedUser.setPassword(DigestUtils.md5Hex(user.getPassword()));
            } else {
                //否则就是修改其他信息，密码直接赋值，以免被覆盖为空
                savedUser.setPassword(user.getPassword());
            }
        }

        savedUser.setPhone(user.getPhone());
        savedUser.setNickname(user.getNickname());
        savedUser.setAvatar(user.getAvatar());
        savedUser.setGender(user.getGender());
        savedUser.setBirthday(user.getBirthday());
        savedUser.setAddress(user.getAddress());
        savedUser.setBanner(user.getBanner());
        log.info("after update" + String.valueOf(savedUser));
        //更新数据
        userMapper.updateUserByWx(savedUser);
        //将修改后的用户信息返回
        return savedUser;
    }

    @Override
    public User findByOpenId(String wxOpenId) {
        return userMapper.fineUserByOpenId(wxOpenId);
    }


    @Override
    public User getUser(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // 读入配置文件信息
        String accessKeyId = aliyunResource.getAccessKeyId();
        String accessKeySecret = aliyunResource.getAccessKeySecret();

        String endpoint = fileResource.getEndPoint();
        log.info(endpoint + ";" + accessKeyId + ";" + accessKeySecret);
        // 创建OSSClient实例
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSS ossClient = new OSSClientBuilder().build("oss-cn-shanghai.aliyuncs.com", "LTAI5tEZS4EkNJdGVV33hAfi", "MgunKpLT3JjWL7dpSCWdShk3l09u5D");
        String fileName = file.getOriginalFilename();
        // 分割文件名，获得文件后缀名
        assert fileName != null;
        String[] fileNameArr = fileName.split("\\.");
        String suffix = fileNameArr[fileNameArr.length - 1];
        //拼接得到新的上传文件名
        String uploadFileName = fileResource.getObjectName() + UUID.randomUUID() + "." + suffix;
        // 上传网络需要用的字节流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            System.err.println("上传文件出现异常");
        }
        //执行阿里云上传文件操作
        ossClient.putObject(fileResource.getBucketName(), uploadFileName, inputStream);
        // 关闭OSSClient
        ossClient.shutdown();
        return uploadFileName;
    }

}
