package com.blog.myblog.service;

import com.blog.myblog.domain.Category;
import com.blog.myblog.domain.CategoryExample;
import com.blog.myblog.mapper.CategoryMapper;
import com.blog.myblog.request.CategoryQueryRequest;
import com.blog.myblog.request.CategorySaveRequest;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.response.CategoryQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService {

    private static final Integer PID = 0;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;


    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResponse> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<Category> categoryWithParent = getCategoryByParent(categoryList);
        List<Category> categoryNotParent = getCategoryNotParent(categoryList);

        List<CategoryQueryResponse> firstParent = CopyUtil.copyList(categoryWithParent, CategoryQueryResponse.class);
        List<CategoryQueryResponse> secondChildren = CopyUtil.copyList(categoryNotParent, CategoryQueryResponse.class);

        for (CategoryQueryResponse c: firstParent) {
            List<CategoryQueryResponse> item = iterateCategory(secondChildren, c.getId().toString());
            c.setChildren(item);
        }

        return firstParent;
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
            category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(DeleteRequest req) {
        categoryMapper.deleteByPrimaryKey(req.getId());
    }


    /**
     * 获取顶级节点
     * @param list
     * @return
     */
    public List<Category> getCategoryByParent(List<Category> list){
        List<Category> result = new ArrayList<>();
        for (Category category: list) {
            if (category.getParentId() == 0) {
                result.add(category);
            }
        }
        return  result;
    }

    /**
     * 获取非顶级节点
     * @param list
     * @return
     */
    public List<Category> getCategoryNotParent(List<Category> list) {
        List<Category> result = new ArrayList<>();
        for (Category category: list) {
            if (category.getParentId() != 0) {
                result.add(category);
            }
        }
        return  result;
    }

    /**
     *多级菜单查询方法
     * @param list 不包含最高层次菜单的菜单集合
     * @param pid 父类id
     * @return
     */
    public List<CategoryQueryResponse> iterateCategory(List<CategoryQueryResponse> list,String pid){
        List<CategoryQueryResponse> result = new ArrayList<>();
        for (CategoryQueryResponse item : list) {
            //获取菜单的id
            String id = item.getId().toString();
            //获取菜单的父id
            String parentId = item.getParentId().toString();

            if(StringUtils.isNotBlank(parentId)){
                if(parentId.equals(pid)){
                    //递归查询当前子菜单的子菜单
                    List<CategoryQueryResponse> iterateCategoryAllResponse = iterateCategory(list,id);
                    item.setChildren(iterateCategoryAllResponse);
                    result.add(item);
                }
            }
        }
        return result;
    }
}
