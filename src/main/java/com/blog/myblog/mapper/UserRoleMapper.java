package com.blog.myblog.mapper;

import com.blog.myblog.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {

    public List<Role> selectUserRole(@Param("id") String id);

}