package com.example.springboot.controller.request;

import lombok.Data;

//分页
@Data
public class BaseRequest {
    //当前页
    private Integer pageNum;
    //每页的数量
    private Integer pageSize;
    //当前页的数量
//    private int size;
}
