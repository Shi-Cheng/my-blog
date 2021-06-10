package com.blog.myblog.service;

import com.blog.myblog.aspect.LogAspect;
import com.blog.myblog.domain.Doc;
import com.blog.myblog.mapper.DocMapper;
import com.blog.myblog.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WsSocketService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private WebSocketServer webSocketServer;

    public void sendInfo(Long id) {
        Doc doc = docMapper.selectByPrimaryKey(id);
        LOG.info("ip: {}", doc.getName());
        webSocketServer.sendInfo("【" + doc.getName() + "】被点赞！");
    }
}
