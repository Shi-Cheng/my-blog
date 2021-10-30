package com.blog.myblog.service;
import com.blog.myblog.domain.*;
import com.blog.myblog.exception.BusinessException;
import com.blog.myblog.exception.BusinessExceptionCode;
import com.blog.myblog.mapper.RoleMapper;
import com.blog.myblog.mapper.UserMapper;
import com.blog.myblog.mapper.UserMenuMapper;
import com.blog.myblog.mapper.UserRoleMapper;
import com.blog.myblog.request.*;
import com.blog.myblog.response.*;
import com.blog.myblog.utils.ArrayListUtil;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserMenuMapper userMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private ArrayListUtil arrayListUtil;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UtilService utilService;
    /**
     * 新增和更新用户
     * @param req
     */
    public void save(UserSaveRequest req) {
        /**
         * 密码加密
         */
        //req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        User user = CopyUtil.copy(req, User.class);
        LOG.info("新增用户：{}", user);
        if (ObjectUtils.isEmpty(user.getId())) {
            User userDB = selectUserByName(req.getName());
            LOG.info("用户：{}", userDB);
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
        User user = selectUserByPhoneNumber(req.getPhoneNumber());
        if (ObjectUtils.isEmpty(user.getPhoneNumber())) {
            // 用户名不存在
            LOG.info("手机号不存在, {}", req.getPhoneNumber());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            String passwordHex = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
            LOG.info("passwordHex:{}, password:{}", passwordHex, user.getPassword());

            System.out.println("======" + user.getPassword().equals(passwordHex));
            if (user.getPassword().equals(passwordHex)) {
                UserLoginResponse response = CopyUtil.copy(user, UserLoginResponse.class);
                return response;
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


    public UserLoginResponse getUserInfo(User userRedis) {
        User user = selectUserByName(userRedis.getName());
        LOG.info("user: {}", user);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        } else {
            // role_menu
            List<RoleMenuCust> roles = userMenuMapper.selectUserMenuList(user.getId().toString());
            //List<RoleMenuResponse> roleMenuResponses = userMenuMapper.selectUserMenuList(user.getId().toString());
            // 用户角色
            List<Role> userRole = userRoleMapper.selectUserRole(user.getId().toString());
            Role role = CopyUtil.copy(userRole.get(0), Role.class);
            //
            List<RoleMenuResponse> roleMenuResponses = CopyUtil.copyList(roles, RoleMenuResponse.class);
            List<String> menuLists = selectMenuId(roleMenuResponses);
            List<String> list = arrayListUtil.duplicate(menuLists);
            UserLoginResponse loginResponse = CopyUtil.copy(user, UserLoginResponse.class);
            loginResponse.setToken(snowFlake.nextId());
            loginResponse.setRoleName(role.getName());
            loginResponse.setRoleDescription(role.getDescription());
            loginResponse.setAccess(list);
            LOG.info("用户登录信息: {}", loginResponse);
            return  loginResponse;
        }
    }

    /**
     * 判断用户是否存在
     * @param loginName
     * @return
     */
    public User selectUserByName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)) {
            LOG.info("登录成功");
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 判断用户手机号是否存在
     * @param phoneNumber
     * @return
     */
    public User selectUserByPhoneNumber(String phoneNumber) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneNumberEqualTo(phoneNumber);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)) {
            LOG.info("登录成功");
            return null;
        } else {
            return users.get(0);
        }
    }

    public Role selectRoleByIdOrName(String type,Object req) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        if (type.equalsIgnoreCase("id")) {
            criteria.andRoleIdEqualTo((Long) req);
        } else {
            criteria.andNameEqualTo(req.toString());
        }
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (CollectionUtils.isEmpty(roles)){
            return  null;
        } else {
            return roles.get(0);
        }
    }

    public List<String> selectMenuId(List<RoleMenuResponse> roles) {
        List<String> roleList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            roleList.add(roles.get(i).getpId());
        }
        return  roleList;
    }

}
