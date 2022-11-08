package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.AdminService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String default_passowrd = "123"; //默认密码123
    private static final String pass_salt = "fangwenjian";
    //查询所有用户信息
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    //获取分页信息
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admin = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admin);
    }

    //新增用户
    @Override
    public void save(Admin admin) {
        if (StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword(default_passowrd);
        }
        admin.setPassword(securePass(admin.getPassword())); //设置MD5加密，加盐
        try {
            adminMapper.save(admin);
        } catch (DuplicateKeyException e) {
                log.error("数据插入失败， username:{}", admin.getUsername(), e);
                throw new ServiceException("用户名已存在");
        }
    }

    @Override
    public Admin getByid(Integer id) {
        return adminMapper.getByid(id);
    }

    @Override
    public void update(Admin admin) {
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        Admin admin = null;
        try {
            admin = adminMapper.getByUsername(loginRequest.getUsername());
        } catch (Exception e) {
            log.error("根据用户名{} 查询出错", loginRequest.getUsername());
            throw new ServiceException("用户名错误");
        }

        if(admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        //判断密码是否合法
        String securePass = securePass(loginRequest.getPassword());
        if (!securePass.equals(admin.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        if(!admin.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);

        //生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }

    @Override
    public void changePass(PasswordRequest passwordRequest) {
        // 注意 要对新的密码进行加密
        passwordRequest.setNewPass(securePass(passwordRequest.getNewPass()));
       int count = adminMapper.updatePassword(passwordRequest);
       if (count <= 0) {
           throw new ServiceException("修改密码失败");
       }
    }

    //密码加密封装供外部调用
    private String securePass ( String password) {
        return SecureUtil.md5(password + pass_salt);
    }
}
