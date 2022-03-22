package com.zzmax.article.mapper;

import com.zzmax.article.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    /**
     * 新增用户，并返回自增主键
     *
     * @param user
     */


    @Insert("INSERT INTO t_user(phone,password,nickname,avatar,gender,birthday,address,create_time) " +
            "VALUES(#{phone},#{password},#{nickname},#{avatar},#{gender},#{birthday},#{address},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(User user);

    /**
     * 根据手机号查询用户信息
     * @return
     */
    @Select("SELECT * FROM t_user WHERE phone = #{phone}")
    User findUserByPhone(@Param("phone") String phone);
}
