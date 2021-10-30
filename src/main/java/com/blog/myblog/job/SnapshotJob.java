package com.blog.myblog.job;
import com.blog.myblog.service.SnapshotService;
import com.blog.myblog.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SnapshotJob {

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotJob.class);

    @Resource
    private SnapshotService snapshotService;

    @Resource
    private SnowFlake snowFlake;

    @Scheduled(cron = "5/30 * * * * ?")
    public void cron() {
        LOG.info("更新snapshot快照");
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        Long startTime = System.currentTimeMillis();
        snapshotService.genSnapshot();

        LOG.info("更新snapshot的表：{}", System.currentTimeMillis() - startTime);
    }
}
