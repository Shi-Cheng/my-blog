package com.blog.myblog.controller;

import com.blog.myblog.request.EBookQueryRequest;
import com.blog.myblog.request.EBookRequest;
import com.blog.myblog.response.EBookQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.EBookResponse;
import com.blog.myblog.service.EBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class EBookController {

    @Resource
    private EBookService eBookService;

    //@GetMapping("/list")
    //public List<EBook> getTestList() {
    //    return eBookService.list();
    //}

    @GetMapping("/getList")
    public CommonResponse list(EBookRequest req) {
        CommonResponse<PageResponse<EBookResponse>> resp = new CommonResponse<>();
        PageResponse<EBookResponse> list = eBookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/queryList")
    public CommonResponse queryList(EBookQueryRequest req) {
        CommonResponse<PageResponse<EBookQueryResponse>> resp = new CommonResponse<>();
        PageResponse<EBookQueryResponse> list = eBookService.queryList(req);
        resp.setContent(list);
        return resp;
    }
}
