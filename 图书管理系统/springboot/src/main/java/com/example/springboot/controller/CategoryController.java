package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

//    第一种 @Resource
//    第二种 @Autowired
    @Autowired
    CategoryService categoryService;



//  @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”）@ApiOperation()
    @ApiOperation(value = "查询所有分类")
    @GetMapping("/list")
    public Result list() {
        List<Category> category = categoryService.list();
        return Result.success(category);
    }

    @GetMapping("/tree")
    public Result tree() {
        List<Category> category = categoryService.list();

        // 对list操作即可
        // 第一层
//        List<Category> treeList = category.stream().filter(v -> v.getPid() == null).collect(Collectors.toList());

        return Result.success(createTree(null,category)); // null 表示从分类第一级开始递归
    }

    // 完全递归的方法来实现递归树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) { // 表示是第一级节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            } else {
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(),categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest) {
        return Result.success(categoryService.page(categoryPageRequest));
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("/save")
    public Result save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    @ApiOperation(value = "根据id获取分类")
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        Category category = categoryService.getByid(id);
        return Result.success(category);
    }

    @ApiOperation(value = "更新分类")
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return Result.success();
    }

}
