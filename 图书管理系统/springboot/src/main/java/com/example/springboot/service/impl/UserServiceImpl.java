package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service //标记当前类是一个service类，加上该注解会将当前类自动注入到spring容器中
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //查询所有用户信息
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    //获取分页信息
    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {

        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

//
    //新增用户
    @Override
    public void save(User user) {
        Date date = new Date();
        //当做卡号来处理             // Math.abs绝对值取正
        user.setUsername(DateUtil.format(date,"yyyymmdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);
    }

    @Override
    public User getByid(Integer id) {
        return userMapper.getByid(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if (score == null) {
            return;
        }
        Integer id = user.getId();
        User dbuser = userMapper.getByid(id);
        dbuser.setAccount(dbuser.getAccount() + score);
        dbuser.setUpdatetime(new Date());
        userMapper.updateById(dbuser);
    }
}
