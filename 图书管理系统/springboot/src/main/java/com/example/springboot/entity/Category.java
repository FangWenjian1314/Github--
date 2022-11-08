package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Category extends BaseEntity{
    private String name;
    private String remark;
    private Integer pid;

    private List<Category> children;
}
