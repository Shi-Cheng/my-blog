package com.blog.myblog.controller;

import com.blog.myblog.domain.Ebook;
import com.blog.myblog.req.EbookRequest;
import com.blog.myblog.resp.CommonResponse;
import com.blog.myblog.resp.EbookResp;
import com.blog.myblog.service.EBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/ebook")
public class EBookController {

    @Resource
    private EBookService eBookService;

    @GetMapping("/list")
    public CommonResponse list(EbookRequest ebookRequest) {
        CommonResponse<List<EbookResp>> response = new CommonResponse<>();
        List<EbookResp> list = eBookService.list(ebookRequest);
        response.setContent(list);
        return response;
    }

}
