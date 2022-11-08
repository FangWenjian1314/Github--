package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    //查询所有的信息
    List<Category> list();  //List<>有序的集合

    //获取分页信息
    PageInfo<Category> page(BaseRequest baseRequest);

    void save(Category category);

    Category getByid(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
