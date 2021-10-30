package com.blog.myblog.service;

import com.blog.myblog.domain.Ebook;
import com.blog.myblog.domain.EbookExample;
import com.blog.myblog.mapper.EbookMapper;
import com.blog.myblog.req.EbookRequest;
import com.blog.myblog.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EBookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookRequest ebookRequest) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + ebookRequest.getName() + "%");

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> ebookRespList = new ArrayList<>();
        for (Ebook ebook: ebookList){
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp);
            ebookRespList.add(ebookResp);
        }

        return ebookRespList;
    }
}
