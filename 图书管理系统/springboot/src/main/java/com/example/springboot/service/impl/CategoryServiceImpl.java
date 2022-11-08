package com.example.springboot.service.impl;

import cn.hutool.core.date.DateTime;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    //获取分页信息
    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        // 自关联查询
        List<Category> category = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(category);
    }

    @Override
    public void save(Category category) {
        categoryMapper.save(category);
    }

    @Override
    public Category getByid(Integer id) {
       return categoryMapper.getById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdatetime(DateTime.now());
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
