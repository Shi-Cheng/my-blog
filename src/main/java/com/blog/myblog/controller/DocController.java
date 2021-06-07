package com.blog.myblog.controller;

import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.DocQueryResponse;
import com.blog.myblog.service.DocService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/getDocTree")
    public CommonResponse getDocTree() {
        CommonResponse commonResponse = new CommonResponse();
        List<DocQueryResponse> docTree = docService.getDocTree();
        commonResponse.setContent(docTree);
        return  commonResponse;
    }
}
