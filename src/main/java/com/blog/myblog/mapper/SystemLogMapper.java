package com.blog.myblog.mapper;

import com.blog.myblog.domain.SystemLog;
import com.blog.myblog.domain.SystemLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemLogMapper {
    long countByExample(SystemLogExample example);

    int deleteByExample(SystemLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    List<SystemLog> selectByExample(SystemLogExample example);

    SystemLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemLog record, @Param("example") SystemLogExample example);

    int updateByExample(@Param("record") SystemLog record, @Param("example") SystemLogExample example);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);
}