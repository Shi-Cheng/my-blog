package com.blog.myblog.mapper;
import com.blog.myblog.response.SnapshotResponse;

import java.util.List;

public interface SnapshotMapperCust {

    public void genSnapshot();

    public List<SnapshotResponse> getStatistic();

    public List<SnapshotResponse> get30Statistic();
}
