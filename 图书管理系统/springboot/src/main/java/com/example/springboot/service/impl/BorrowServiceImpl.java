package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.ReturnBook;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.BorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BorrowServiceImpl implements BorrowService {

    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;


    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    //获取分页信息
    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)) { // 当前日期比归还的日期少一天
                borrow.setNote("即将到期");
            } else if (now.isEqual(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已过期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    @Transactional
    public void save(Borrow borrow) {
        // 校验用户是否存在
        String userNo = borrow.getUserNo();
        User user = userMapper.getByUserName(userNo);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }

        // 校验图书是否存在
        Book book = bookMapper.getByNo(borrow.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }

        // 校验用户的积分是否足够
        Integer account = user.getAccount();
        Integer score = book.getScore() * borrow.getDays(); // score = 借1本所需积分 * 天数
        if (score > account) {
            throw new ServiceException("用户积分不足");
        }

        // 校验图书的数量是否足够
        if (book.getNums() < 1) {
            throw new ServiceException("图书数量不足");
        }
        // 更新余额
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);
        // 更新图书的数量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

        // 当前的日期加 days 得到 归还日期
        borrow.setReturnDate(LocalDate.now().plus(borrow.getDays(), ChronoUnit.DAYS));
        borrow.setScore(score);
        // 新增借书记录
        borrowMapper.save(borrow);
    }

    @Override
    public Borrow getByid(Integer id) {
       return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow borrow) {
        borrow.setUpdatetime(new Date());
        borrowMapper.updateById(borrow);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    @Override
    public PageInfo<ReturnBook> pageReturn(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listReturnByCondition(baseRequest));
    }

    // 还书逻辑
    @Transactional
    @Override
    public void saveReturn(ReturnBook returnBook) {
        // 改状态
        returnBook.setStatus("已归还");
        borrowMapper.updateStatus("已归还",returnBook.getId()); // 前端传来的借书id

//        returnBook.setId(null); // 新数据
        returnBook.setRealDate(LocalDate.now());
        borrowMapper.saveReturn(returnBook);

        // 图书数量增加
        borrowMapper.updateNumByNo(returnBook.getBookNo());

        // 返还和扣除用户积分
        Book book = bookMapper.getByNo(returnBook.getBookNo());
        if (book != null) {
            long until = 0;
            if (returnBook.getRealDate().isBefore(returnBook.getReturnDate())) {
                until = returnBook.getRealDate().until(returnBook.getReturnDate(), ChronoUnit.DAYS);
            } else if (returnBook.getRealDate().isAfter(returnBook.getReturnDate())) { // 逾期归还，要扣额外的积分
                until = - returnBook.getReturnDate().until(returnBook.getRealDate(), ChronoUnit.DAYS);
            }
            int score = (int) until * book.getScore(); // 获取待归还的积分
            User user = userMapper.getByUserName(returnBook.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (account < 0) {
                    // 锁定账号
                    user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    @Override
    public void deleteReturnById(Integer id) {
        borrowMapper.deleteReturnById(id);
    }

}
