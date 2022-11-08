package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;


    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    //获取分页信息
    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(bookMapper.listByCondition(baseRequest));
    }

    @Override
    public void save(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            bookMapper.save(book);
        } catch (Exception e) {
            throw new ServiceException("数据插入错误",e);
        }
    }

    @Override
    public Book getByid(Integer id) {
       return bookMapper.getById(id);
    }

    @Override
    public void update(Book book) {
        try {
            book.setCategory(category(book.getCategories()));
            book.setUpdatetime(new Date());
            bookMapper.updateById(book);
        } catch (Exception e) {
            throw new ServiceException("数据更新错误", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    private String category(List<String> categories) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> stringBuilder.append(v).append(" > "));
            return stringBuilder.substring(0,stringBuilder.lastIndexOf(" > "));
        }
        return stringBuilder.toString();
    }
}
