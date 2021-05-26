package com.blog.myblog.service;

import com.blog.myblog.domain.EBook;
import com.blog.myblog.domain.EBookExample;
import com.blog.myblog.mapper.EBookMapper;
import com.blog.myblog.request.EBookRequest;
import com.blog.myblog.response.EBookResponse;
import com.blog.myblog.utils.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EBookService {

    @Resource
    private EBookMapper eBookMapper;

    public List<EBookResponse> list(EBookRequest req) {
        EBookExample eBookExample = new EBookExample();
        EBookExample.Criteria criteria = eBookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        //criteria.andDescriptionLike("%" + req.getDescription() + "%");

        List<EBook> eBookList = eBookMapper.selectByExample(eBookExample);
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

        return eBookResponseList;
    }

}
