package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Book;
import com.example.springboot.service.BookService;
import com.example.springboot.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

//    第一种 @Resource
//    第二种 @Autowired
    @Autowired
    BookService bookService;



//  @ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”）@ApiOperation()

  // 获取当前的项目的根目录
    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";

    @PostMapping("/file/upload")
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "-" + originalFilename;
        try {
            FileUtil.mkParentDirs(filePath); // 创建父级目录
            file.transferTo(FileUtil.file(filePath));
            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.genToken(currentAdmin.getId().toString(), currentAdmin.getPassword(), 15);
            String url = "http://localhost:8080/api/book/file/download/" + flag + "?&token=" + token;
            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpg") || originalFilename.endsWith("pdf") || originalFilename.endsWith("jpeg") ) {
                url += "&play=1"; // play=1表示预览，不要则是下载
            }
            return Result.success(url);
        } catch (Exception e) {
            log.info("文件上传失败", e);
        }
        return Result.error("文件上传失败");
    }

    @GetMapping("/file/download/{flag}")
    public void downloadFile(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse(""); // System.currentTimeMillis() + originalFilename
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }

    @ApiOperation(value = "查询所有分类")
    @GetMapping("/list")
    public Result list() {
        List<Book> book = bookService.list();
        return Result.success(book);
    }

    @ApiOperation(value = "获取分页")
    @GetMapping("/page")
    public Result page(BookPageRequest bookPageRequest) {
        return Result.success(bookService.page(bookPageRequest));
    }

    @ApiOperation(value = "新增分类")
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        bookService.save(book);
        return Result.success();
    }

    @ApiOperation(value = "根据id获取分类")
    @GetMapping("/{id}")
    public Result getByid(@PathVariable Integer id) {
        Book book = bookService.getByid(id);
        return Result.success(book);
    }

    @ApiOperation(value = "更新分类")
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        bookService.update(book);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除分类")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

}
