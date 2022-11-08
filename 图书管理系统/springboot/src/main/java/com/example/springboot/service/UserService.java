package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    //查询所有的信息
    List<User> list();  //List<>有序的集合

    //获取分页信息
    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getByid(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void handleAccount(User user);
}
