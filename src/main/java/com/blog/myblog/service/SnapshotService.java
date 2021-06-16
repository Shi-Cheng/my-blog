package com.blog.myblog.service;

import com.blog.myblog.mapper.SnapshotMapperCust;
import com.blog.myblog.response.SnapshotResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SnapshotService {

    @Resource
    private SnapshotMapperCust snapshotMapperCust;
    //
    public void genSnapshot() {
        snapshotMapperCust.genSnapshot();
    }

    public List<SnapshotResponse> getStatistic(){
        List<SnapshotResponse> statistic = snapshotMapperCust.getStatistic();
        return statistic;
    }

    public List<SnapshotResponse> get30Statistic(){
        List<SnapshotResponse> statistic = snapshotMapperCust.get30Statistic();
        return statistic;
    }
}
