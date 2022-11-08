package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

//    第一种 @Resource
//    第二种 @Autowired
    @Autowired
    AdminService adminService;



//  @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”）@ApiOperation()
    @ApiOperation(value = "管理员登陆")
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO login = adminService.login(loginRequest);
        return Result.success(login);
    }

    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest passwordRequest) {
        adminService.changePass(passwordRequest);
        return Result.success();
    }

    @ApiOperation(value = "查询所有用户")
    @GetMapping("/list")
    public Result list() {
        List<Admin> admin = adminService.list();
        return Result.success(admin);
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest) {
        return Result.success(adminService.page(adminPageRequest));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success();
    }

    @ApiOperation(value = "根据id获取用户")
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        Admin admin = adminService.getByid(id);
        return Result.success(admin);
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        admin.setUpdatetime(new Date());
        adminService.update(admin);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

}
