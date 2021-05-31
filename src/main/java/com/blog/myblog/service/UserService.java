package com.blog.myblog.service;

import com.blog.myblog.domain.User;
import com.blog.myblog.domain.UserExample;
import com.blog.myblog.mapper.UserMapper;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.request.UserQueryRequest;
import com.blog.myblog.request.UserSaveRequest;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.response.UserQueryResponse;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserMapper userMapper;

    public void save(UserSaveRequest req) {
        User user = CopyUtil.copy(req, User.class);
        if (!ObjectUtils.isEmpty(user.getId())) {
            // 新增用户
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        } else{
            // 更新用户
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(DeleteRequest req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(user.getId())) {
            userMapper.deleteByPrimaryKey(req.getId());
        }
    }

    public PageResponse<UserQueryResponse> list(UserQueryRequest req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameLike("%" + req.getLoginName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);

        List<UserQueryResponse> responseList = CopyUtil.copyList(userList, UserQueryResponse.class);
        PageResponse<UserQueryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(responseList);
        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setPage(pageInfo.getPages());
        return pageResponse;
    }
}
