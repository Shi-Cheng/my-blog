package com.blog.myblog.mapper;

import com.blog.myblog.domain.RoleMenu;
import com.blog.myblog.domain.RoleMenuCust;
import com.blog.myblog.response.RoleMenuResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMenuMapper {

    List<RoleMenuCust> selectUserMenuList(@Param("id") String id);

    List<RoleMenu> selectMenu(@Param("id") Integer id);
}