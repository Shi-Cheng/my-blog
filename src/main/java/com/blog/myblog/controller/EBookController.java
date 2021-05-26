package com.blog.myblog.controller;

import com.blog.myblog.domain.EBook;
import com.blog.myblog.request.EBookRequest;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.EBookResponse;
import com.blog.myblog.service.EBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
        CommonResponse<List<EBookResponse>> resp = new CommonResponse<>();
        List<EBookResponse> list = eBookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
