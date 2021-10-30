package com.blog.myblog.service;
import com.blog.myblog.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleService {

    private final static Logger LOG = LoggerFactory.getLogger(RoleService.class);

    @Resource
    private RoleMapper roleMapper;



}
