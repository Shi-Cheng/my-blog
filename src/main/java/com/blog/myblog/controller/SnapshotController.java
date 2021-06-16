package com.blog.myblog.controller;

import com.blog.myblog.response.CommonResponse;
import com.blog.myblog.response.SnapshotResponse;
import com.blog.myblog.service.SnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class SnapshotController {

    @Resource
    private SnapshotService snapshotService;

    @GetMapping("/genStatistic")
    public CommonResponse genStatistic() {
        CommonResponse commonResponse = new CommonResponse();
        snapshotService.genSnapshot();
        return  commonResponse;
    }

    @GetMapping("/getStatisticList")
    public CommonResponse<List<SnapshotResponse>> getStatisticList() {
        CommonResponse<List<SnapshotResponse>> commonResponse = new CommonResponse<>();
        List<SnapshotResponse> statistic = snapshotService.getStatistic();

        commonResponse.setData(statistic);
        return  commonResponse;
    }

    @GetMapping("/getStatistic30List")
    public CommonResponse<List<SnapshotResponse>> getStatistic30List() {
        CommonResponse<List<SnapshotResponse>> commonResponse = new CommonResponse<>();
        List<SnapshotResponse> statistic = snapshotService.get30Statistic();
        commonResponse.setData(statistic);
        return commonResponse;
    }
}
