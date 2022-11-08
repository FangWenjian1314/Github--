package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    //查询所有的信息
    List<Admin> list();  //List<>有序的集合

    //获取分页信息
    PageInfo<Admin> page(BaseRequest baseRequest);

    void save(Admin admin);

    Admin getByid(Integer id);

    void update(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest loginRequest);

    void changePass(PasswordRequest passwordRequest);
}
