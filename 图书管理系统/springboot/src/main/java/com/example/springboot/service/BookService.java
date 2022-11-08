package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BookService {
    //查询所有的信息
    List<Book> list();  //List<>有序的集合

    //获取分页信息
    PageInfo<Book> page(BaseRequest baseRequest);

    void save(Book book);

    Book getByid(Integer id);

    void update(Book book);

    void deleteById(Integer id);
}
