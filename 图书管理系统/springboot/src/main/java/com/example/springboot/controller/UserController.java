package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin  //用来处理跨域请求的注解，比如Ajax无法访问远程服务器上的API
@RestController
@RequestMapping("/user")
public class UserController {

//    第一种 @Resource
//    第二种 @Autowired

    //对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 通过 @Autowired的使用来消除 set ，get方法。
    //@Autowired注解调用UserService接口
    @Autowired
    UserService userService;



//  @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”)
    @ApiOperation(value = "查询所有用户")
    /**
     * @RequestMapping(value = “/get/{id}”, method = RequestMethod.GET)
     * 新方法可以简化为：@GetMapping("/get/{id}")
     * @GetMapping 一般处理单个id,比如getById
     **/
    @GetMapping("/list")
    public Result list() {
        List<User> users = userService.list();
        return Result.success(users);
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }

    @ApiOperation(value = "新增用户")
    /**@PostMapping 一般处理对象的findUser
     * @PostMapping 等同于 RequestMapping(value = “/get”, method = RequestMethod.POST)
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @PostMapping("/account")
    public Result account(@RequestBody User user) {
        userService.handleAccount(user);
        return Result.success();
    }

    @ApiOperation(value = "根据id获取用户")
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        User user = userService.getByid(id);
        return Result.success(user);
    }

    @ApiOperation(value = "更新用户")
    /**
     *  @PutMapper 一般用在编辑，也可以只用上面两种
     *  等同于 @RequestMapping(value = "/get",method = RequestMethod.PUT)
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        user.setUpdatetime(new Date());
        userService.update(user);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除用户")
    /**
     * @DeleteMapping 通常在删除数据的时候使用
     * 等同于 @RequestMapping(value = "/get",method = RequestMethod.DELETE)
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.success();
    }

}
