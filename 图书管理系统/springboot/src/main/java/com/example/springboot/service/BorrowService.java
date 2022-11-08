package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BorrowService {
    //查询所有的信息
    List<Borrow> list();  //List<>有序的集合

    //获取分页信息
    PageInfo<Borrow> page(BaseRequest baseRequest);

    void save(Borrow borrow);

    Borrow getByid(Integer id);

    void update(Borrow borrow);

    void deleteById(Integer id);

//    还书
    PageInfo<ReturnBook> pageReturn(BaseRequest baseRequest);

    void saveReturn(ReturnBook returnBook);

    void deleteReturnById(Integer id);
}
