package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper // 加了这个注解会自动帮我们实现一个该接口的代理类
public interface UserMapper {

    //使用List接受
    //[{id:1,name:"小明"},{id:2,name:"小红"}]

    // 查询所有用户信息
    List<User> list();

    //获取分页信息
    List<User> listByCondition(BaseRequest baseRequest);

    //新增用户
    void save(User user);

    //根据id获取用户
    User getByid(Integer id);

    //更新用户信息
    void updateById(User user);

    //根据id删除
    void deleteById(Integer id);


    User getByUserName(String userName);

}
