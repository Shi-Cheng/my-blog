package com.blog.myblog.service;

import com.blog.myblog.config.ControllerExceptionHandler;
import com.blog.myblog.domain.Category;
import com.blog.myblog.domain.CategoryExample;
import com.blog.myblog.mapper.CategoryMapper;
import com.blog.myblog.request.CategoryQueryRequest;
import com.blog.myblog.request.CategorySaveRequest;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.request.QueryAllRequest;
import com.blog.myblog.response.CategoryQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResponse> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResponse> responseList = CopyUtil.copyList(categoryList, CategoryQueryResponse.class);

        return responseList;
    }

    public PageResponse<CategoryQueryResponse> list(CategoryQueryRequest req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        if(!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数： {}", pageInfo.getTotal());
        LOG.info("总页数： {}", pageInfo.getPages());

        List<CategoryQueryResponse> responseList = CopyUtil.copyList(categoryList, CategoryQueryResponse.class);

        PageResponse<CategoryQueryResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageInfo.getPages());
        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setList(responseList);

        return pageResponse;
    }

    public void save(CategorySaveRequest req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (!ObjectUtils.isEmpty(req.getId())) {
            // 新增
            //category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        } else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(DeleteRequest req) {
        categoryMapper.deleteByPrimaryKey(req.getId());
    }
}
