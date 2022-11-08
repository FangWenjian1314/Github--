package com.example.springboot.controller.request;

import lombok.Data;

//以username/phone/email获取分页
@Data
public class AdminPageRequest extends BaseRequest{
    private String username;
    private String phone;
    private String email;

}
