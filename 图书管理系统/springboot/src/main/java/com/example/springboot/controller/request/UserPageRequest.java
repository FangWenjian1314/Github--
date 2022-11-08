package com.example.springboot.controller.request;

import lombok.Data;

//以name和phone获取分页
@Data
public class UserPageRequest extends BaseRequest{
    private String name;
    private String phone;

}
