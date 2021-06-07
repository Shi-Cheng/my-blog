package com.blog.myblog.controller;

import com.blog.myblog.domain.Doc;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.request.DocRequest;
import com.blog.myblog.request.DocSaveRequest;
import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.DocQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

    @GetMapping("/getDocTree")
    public CommonResponse getDocTree() {
        CommonResponse commonResponse = new CommonResponse<>();
        List<DocQueryResponse> docTree = docService.getDocTree();
        commonResponse.setContent(docTree);
        return  commonResponse;
    }

    @GetMapping("/list")
    public CommonResponse list(@Valid DocRequest req) {
        CommonResponse commonResponse = new CommonResponse<>();
        PageResponse<DocQueryResponse> docQueryResponseList = docService.list(req);
        commonResponse.setContent(docQueryResponseList);
        return commonResponse;
    }

    @PostMapping("/add")
    public CommonResponse add(@Valid @RequestBody DocSaveRequest req) {
        CommonResponse commonResponse  = new CommonResponse<>();
        docService.add(req);
        return  commonResponse;
    }

    @PostMapping("/delete")
    public CommonResponse delete(@RequestBody DeleteRequest req) {
        CommonResponse commonResponse = new CommonResponse<>();
        docService.delete(req);
        return commonResponse;
    }

    /**
     * 文档点赞功能， 同一个IP一天只能点赞一次
     * @param id
     * @return
     */
    @GetMapping("/vote/{id}")
    public CommonResponse vote(@PathVariable Long id) {
        CommonResponse commonResponse = new CommonResponse<>();
        docService.vote(id);
        return  commonResponse;
    }

    /**
     * 文档及文档阅读数
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public CommonResponse findContent(@PathVariable Long id) {
        CommonResponse commonResponse = new CommonResponse<>();
        Doc doc = docService.findContent(id);
        commonResponse.setContent(doc);
        return commonResponse;
    }

}
