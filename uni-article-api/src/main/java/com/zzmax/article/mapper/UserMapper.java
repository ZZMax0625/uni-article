package com.zzmax.article.mapper;

import com.zzmax.article.model.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    /**
     * 新增用户，并返回自增主键
     *
     * @param
     */
    @Insert("INSERT INTO t_user (phone,wx_openid,password,nickname,avatar,gender,birthday,address,banner,create_time) "
            + "VALUES (#{phone},#{wxOpenId}, #{password}, #{nickname},#{avatar}, #{gender}, #{birthday},#{address},#{banner},#{createTime}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);




    /**
     * 修改用户信息
     *
     * @param user 用户对象
     */
    @Update("UPDATE t_user SET password=#{password},nickname=#{nickname},avatar=#{avatar},phone=#{phone}," +
            "gender=#{gender},birthday=#{birthday},address=#{address},banner=#{banner} WHERE id=#{id} ")
    void updateUser(User user);

    @Update("UPDATE t_user SET password=#{password},nickname=#{nickname},avatar=#{avatar},phone=#{phone}," +
            "gender=#{gender},birthday=#{birthday},address=#{address},banner=#{banner} WHERE wx_openid=#{wxOpenId} ")
    void updateUserByWx(User user);

    /**
     * 根据手机号查询用户信息
     * @return
     */
    @Select("SELECT * FROM t_user WHERE phone = #{phone}")
    User findUserByPhone(@Param("phone") String phone);

//    @Select("SELECT * FROM t_user WHERE phone = #{wxOpenId}")
//    User findUserByWx(@Param("phone") String wxOpenId);

    /**
     * 根据微信openId查询用户信息
     *
     * @param wxOpenId 微信openId
     * @return User 用户对象
     */
    @Select("SELECT * FROM t_user WHERE wx_openid = #{wxOpenId} ")
    User fineUserByOpenId(@Param("wxOpenId") String wxOpenId);

}
