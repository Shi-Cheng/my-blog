package com.blog.myblog.service;

import com.blog.myblog.domain.EBook;
import com.blog.myblog.domain.EBookExample;
import com.blog.myblog.mapper.EBookMapper;
import com.blog.myblog.request.EBookQueryRequest;
import com.blog.myblog.request.EBookRequest;
import com.blog.myblog.response.EBookQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.response.EBookResponse;
import com.blog.myblog.utils.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EBookService {

    private static final Logger LOG = LoggerFactory.getLogger(EBookService.class);

    @Resource
    private EBookMapper eBookMapper;

    public PageResponse<EBookResponse> list(EBookRequest req) {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getDescription())){
            criteria.andDescriptionLike("%" + req.getDescription() + "%");
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<EBook> eBookList = eBookMapper.selectByExample(eBookExample);

        PageInfo<EBook> pageInfo = new PageInfo<>(eBookList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        // List<EBookResponse> eBookResponseList = new ArrayList<>();
        //for (EBook eBook : eBookList) {
        //    //EBookResponse eBookResponse = new EBookResponse();
        //    //BeanUtils.copyProperties(eBook, eBookResponse);
        //    //单个对象的 copy
        //    EBookResponse ebookResponseCopy = CopyUtil.copy(eBook, EBookResponse.class);
        //    eBookResponseList.add(ebookResponseCopy);
        //}

        // 列表对象的复制
        List<EBookResponse> eBookResponseList = CopyUtil.copyList(eBookList, EBookResponse.class);

        PageResponse<EBookResponse>  pageResponse = new PageResponse<>();

        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setList(eBookResponseList);
        return pageResponse;
    }


    public PageResponse<EBookQueryResponse> queryList(EBookQueryRequest req) {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getDescription())) {
            criteria.andDescriptionLike("%" + req.getDescription() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());

        List<EBook> eBookList = eBookMapper.selectByExample(eBookExample);

        PageInfo<EBook> pageInfo = new PageInfo<>(eBookList);

        List<EBookQueryResponse> eBookQueryResponses = CopyUtil.copyList(eBookList, EBookQueryResponse.class);

        PageResponse<EBookQueryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(eBookQueryResponses);
        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setPage(pageInfo.getPages());
        LOG.info("page {}", pageInfo.getPages());
        LOG.info("pageNum {}", pageInfo.getPageNum());
        return  pageResponse;

    }
}
