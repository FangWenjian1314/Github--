package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.example.springboot.service.BorrowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

//    第一种 @Resource
//    第二种 @Autowired
    @Autowired
    BorrowService borrowService;



//  @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”）@ApiOperation()
    @ApiOperation(value = "查询所有分类")
    @GetMapping("/list")
    public Result list() {
        List<Borrow> borrow = borrowService.list();
        return Result.success(borrow);
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    public Result page(BorrowPageRequest borrowPageRequest) {
        return Result.success(borrowService.page(borrowPageRequest));
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("/save")
    public Result save(@RequestBody Borrow borrow) {
        borrowService.save(borrow);
        return Result.success();
    }

    @ApiOperation(value = "根据id获取分类")
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        Borrow borrow = borrowService.getByid(id);
        return Result.success(borrow);
    }

    @ApiOperation(value = "更新分类")
    @PutMapping("/update")
    public Result update(@RequestBody Borrow borrow) {
        borrowService.update(borrow);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        borrowService.deleteById(id);
        return Result.success();
    }

//    还书
    @ApiOperation(value = "获取分页")
    @GetMapping("/pageReturn")
    public Result pageReturn(BorrowPageRequest borrowPageRequest) {
        return Result.success(borrowService.pageReturn(borrowPageRequest));
    }

    @ApiOperation(value = "")
    @PostMapping("/saveReturn")
    public Result saveReturn(@RequestBody ReturnBook returnBook) {
        borrowService.saveReturn(returnBook);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/deleteReturn/{id}")
    public Result deleteReturn(@PathVariable Integer id) {
        borrowService.deleteReturnById(id);
        return Result.success();
    }

}
