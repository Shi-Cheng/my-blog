package com.blog.myblog.service;

import com.blog.myblog.domain.User;
import com.blog.myblog.domain.UserExample;
import com.blog.myblog.exception.BusinessException;
import com.blog.myblog.exception.BusinessExceptionCode;
import com.blog.myblog.mapper.UserMapper;
import com.blog.myblog.request.*;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.response.UserLoginResponse;
import com.blog.myblog.response.UserQueryResponse;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserMapper userMapper;

    /**
     * 新增和更新用户
     * @param req
     */
    public void save(UserSaveRequest req) {
        /**
         * 密码加密
         */
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        User user = CopyUtil.copy(req, User.class);
        LOG.info("新增用户：{}", user);
        if (ObjectUtils.isEmpty(user.getId())) {
            User userDB = selectUserByName(req.getName());
            if (ObjectUtils.isEmpty(userDB)) {
                // 新增用户
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户名存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else{
            // 更新用户
            // 不更新 用户名和密码
            user.setName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 删除用户
     * @param req
     */
    public void delete(DeleteRequest req) {
        User user = CopyUtil.copy(req, User.class);
        if (!ObjectUtils.isEmpty(user.getId())) {
            userMapper.deleteByPrimaryKey(req.getId());
        }
    }

    /**
     * 用户查询
     * @param req
     * @return
     */
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

    /**
     * 用户登录
     * @param req
     * @return
     */
    public UserLoginResponse login(UserLoginRequest req) {
        User user = selectUserByName(req.getLoginName());
        if (ObjectUtils.isEmpty(user.getLoginName())) {
            // 用户名不存在
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (user.getPassword().equals(req.getPassword())) {
                UserLoginResponse loginResponse = CopyUtil.copy(user, UserLoginResponse.class);
                return loginResponse;
            } else {
                // 密码不正确
                LOG.info("用户密码不正确, 输入密码{}, 数据库密码{}", req.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    /**
     * 重置密码
     * @param req
     */
    public void resetPassword(UserResetPasswordRequest req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 判断用户是否存在
     * @param loginName
     * @return
     */
    public User selectUserByName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (ObjectUtils.isEmpty(loginName)) {
            criteria.andNameEqualTo(loginName);
        }

        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)) {
            LOG.info("登录成功");
            return null;
        } else {
            return users.get(0);
        }
    }
}
