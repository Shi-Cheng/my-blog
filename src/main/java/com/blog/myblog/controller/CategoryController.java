package com.blog.myblog.controller;

import com.blog.myblog.request.CategoryQueryRequest;
import com.blog.myblog.request.CategorySaveRequest;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.response.CategoryQueryResponse;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResponse list(CategoryQueryRequest req){
        CommonResponse<PageResponse<CategoryQueryResponse>> response = new CommonResponse<>();
        PageResponse<CategoryQueryResponse> list = categoryService.list(req);
        response.setContent(list);
        return  response;
    }

    @PostMapping("/save")
    public CommonResponse save(@RequestBody CategorySaveRequest req) {
        CommonResponse response = new CommonResponse<>();
        categoryService.save(req);
        return  response;
    }

    @PostMapping("/deleted")
    public CommonResponse delete(@RequestBody DeleteRequest req) {
        CommonResponse response = new CommonResponse<>();
        categoryService.delete(req);
        return  response;
    }
}
