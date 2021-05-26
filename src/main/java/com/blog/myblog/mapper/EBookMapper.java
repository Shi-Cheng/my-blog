package com.blog.myblog.mapper;

import com.blog.myblog.domain.EBook;
import com.blog.myblog.domain.EBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EBookMapper {
    long countByExample(EBookExample example);

    int deleteByExample(EBookExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EBook record);

    int insertSelective(EBook record);

    List<EBook> selectByExample(EBookExample example);

    EBook selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EBook record, @Param("example") EBookExample example);

    int updateByExample(@Param("record") EBook record, @Param("example") EBookExample example);

    int updateByPrimaryKeySelective(EBook record);

    int updateByPrimaryKey(EBook record);
}