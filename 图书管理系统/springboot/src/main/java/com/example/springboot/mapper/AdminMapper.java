package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    //查询所有用户信息
    List<Admin> list();

    //获取分页信息
    List<Admin> listByCondition(BaseRequest baseRequest);

    //新增用户
    void save(Admin admin);

    //根据id获取用户
    Admin getByid(Integer id);

    //更新用户信息
    void updateById(Admin admin);

    //根据id删除用户
    void deleteById(Integer id);

    Admin getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int updatePassword(PasswordRequest passwordRequest);

    Admin getByUsername(String username);
}
