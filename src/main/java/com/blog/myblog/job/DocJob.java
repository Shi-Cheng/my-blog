//package com.blog.myblog.job;
//import com.blog.myblog.service.DocService;
//import com.blog.myblog.utils.SnowFlake;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Component
//public class DocJob {
//
//    private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);
//
//    @Resource
//    private DocService docService;
//
//    @Resource
//    private SnowFlake snowFlake;
//    /**
//     * 自定义cron表达式
//     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
//     */
//    @Scheduled(cron = "5/30 * * * * ?")
//    public void cron() {
//        LOG.info("更新电子书下的文档数据开始");
//        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
//        long start = System.currentTimeMillis();
//        docService.updateEbookInfo();
//        LOG.info("更新电子书下的文档数据结束：{}", System.currentTimeMillis() - start);
//    }
//}
