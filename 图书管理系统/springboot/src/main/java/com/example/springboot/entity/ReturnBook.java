package com.example.springboot.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnBook extends BaseEntity{

    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 图书标准码
     */
    private String bookNo;

    /**
     * 用户会员码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户联系方式
     */
    private String userPhone;

    /**
     * 借书积分
     */
    private Integer score;

    /**
     * 借书状态
     */
    private String status;

    /**
     * 借书天数
     */
    private Integer days;

    /**
     * 归还日期
     */
    private LocalDate returnDate;

    /**
     * 实际归还日期
     */
    private LocalDate realDate;
}
