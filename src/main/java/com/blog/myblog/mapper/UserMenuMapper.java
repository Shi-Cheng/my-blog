package com.blog.myblog.mapper;

import com.blog.myblog.domain.Role;
import com.blog.myblog.domain.RoleMenu;
import com.blog.myblog.response.RoleMenuResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMenuMapper {

    public List<RoleMenu> selectUserMenuList(@Param("id") String id);

    public List<RoleMenu> selectMenu(@Param("id") Integer id);
}