package com.blog.myblog.controller;

import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.request.EBookQueryRequest;
import com.blog.myblog.request.EBookRequest;
import com.blog.myblog.request.EBookSaveRequest;
import com.blog.myblog.response.*;
import com.blog.myblog.service.EBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public CommonResponse<PageResponse<EBookResponse>> list(EBookRequest req) {
        CommonResponse<PageResponse<EBookResponse>> resp = new CommonResponse<>();
        PageResponse<EBookResponse> list = eBookService.list(req);
        resp.setData(list);
        return resp;
    }

    @GetMapping("/queryList")
    public CommonResponse<PageResponse<EBookQueryResponse>> queryList(@Valid EBookQueryRequest req) {
        CommonResponse<PageResponse<EBookQueryResponse>> resp = new CommonResponse<>();
        PageResponse<EBookQueryResponse> list = eBookService.queryList(req);
        resp.setData(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResponse<EBookResponse> save(@RequestBody EBookSaveRequest req){
        CommonResponse<EBookResponse> saveResponse = new CommonResponse<>();
        eBookService.save(req);
        return saveResponse;
    }

    @PostMapping("/deleted")
    public CommonResponse<DeleteRequest> delete(@RequestBody DeleteRequest req) {
        CommonResponse<DeleteRequest> res = new CommonResponse<>();
        eBookService.delete(req);
        return res;
    }
}
