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
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping("/all")
    public CommonResponse all(){
        CommonResponse<List<CategoryQueryResponse>> response = new CommonResponse<>();
        List<CategoryQueryResponse> list = categoryService.all();
        response.setData(list);
        return  response;
    }

    /**
     * 分类列表
     * @param req
     * @return
     */
    @GetMapping("/list")
    public CommonResponse list(@Valid CategoryQueryRequest req){
        CommonResponse<PageResponse<CategoryQueryResponse>> response = new CommonResponse<>();
        PageResponse<CategoryQueryResponse> list = categoryService.list(req);
        response.setData(list);
        return  response;
    }

    /**
     * 新增或更新分类
     * @param req
     * @return
     */
    @PostMapping("/save")
    public CommonResponse save(@Valid @RequestBody CategorySaveRequest req) {
        CommonResponse response = new CommonResponse<>();
        categoryService.save(req);
        return  response;
    }

    /**
     * 删除分类
     * @param req
     * @return
     */
    @PostMapping("/deleted")
    public CommonResponse delete(@Valid @RequestBody DeleteRequest req) {
        CommonResponse response = new CommonResponse<>();
        categoryService.delete(req);
        return  response;
    }
}
