package com.blog.myblog.mapper;
import org.apache.ibatis.annotations.Param;

public interface DocMapperCust {

    void increaseDocVoteCount(@Param("id") Long id);

    void increaseDocViewCount(@Param("id") Long id);

    void updateEbookInfo();
}