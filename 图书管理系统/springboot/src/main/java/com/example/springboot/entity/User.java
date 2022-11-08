package com.example.springboot.entity;

import lombok.Data;


@Data
public class User extends BaseEntity{
     private String name;
     private String username;
     private Integer age;
     private Integer account;
     private Integer score;
     private String sex;
     private String phone;
     private String address;
     private boolean status;
}
