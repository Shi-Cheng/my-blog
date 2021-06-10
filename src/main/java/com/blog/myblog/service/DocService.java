package com.blog.myblog.service;

import com.blog.myblog.domain.Doc;
import com.blog.myblog.domain.DocExample;
import com.blog.myblog.exception.BusinessException;
import com.blog.myblog.exception.BusinessExceptionCode;
import com.blog.myblog.mapper.DocMapper;
import com.blog.myblog.mapper.DocMapperCust;
import com.blog.myblog.request.DeleteRequest;
import com.blog.myblog.request.DocRequest;
import com.blog.myblog.request.DocSaveRequest;
import com.blog.myblog.response.DocQueryResponse;
import com.blog.myblog.response.PageResponse;
import com.blog.myblog.utils.CopyUtil;
import com.blog.myblog.utils.RedisUtil;
import com.blog.myblog.utils.RequestContext;
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
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private WsSocketService wsSocketService;

    public void add(DocSaveRequest req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        if (ObjectUtils.isEmpty(req.getId())){
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public PageResponse<DocQueryResponse> list(DocRequest req) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();

        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docs = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docs);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        List<DocQueryResponse> docQueryResponses = CopyUtil.copyList(docs, DocQueryResponse.class);

        PageResponse<DocQueryResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(pageInfo.getPages());
        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setList(docQueryResponses);

        return  pageResponse;
    }

    public List<DocQueryResponse> getDocTree() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");

        List<Doc> docLists = docMapper.selectByExample(docExample);
        List<DocQueryResponse> docQueryResponses = CopyUtil.copyList(docLists, DocQueryResponse.class);
        List<Doc> parentDocList = getParentList(docLists);
        List<Doc> childrenDocList = getChildrenList(docLists);

        List<DocQueryResponse> docQueryParentList = CopyUtil.copyList(parentDocList, DocQueryResponse.class);
        List<DocQueryResponse> docQueryChildrenList = CopyUtil.copyList(childrenDocList, DocQueryResponse.class);
        for (DocQueryResponse d: docQueryParentList) {
            List<DocQueryResponse> itemResponse = iteratorList(docQueryChildrenList, d.getId().toString());
            d.setChildren(itemResponse);
        }
        return docQueryParentList;
    }

    public Doc findContent(Long id) {
        Doc doc = docMapper.selectByPrimaryKey(id);
        docMapperCust.increaseDocViewCount(id);
        return doc;
    }

    public void vote(Long id) {
        String ip = RequestContext.getRemoteAddr();
        LOG.info("ip: {}", ip);
        // 远程 id 和 ip 作为key， 24小时内不能重复 3600 * 24
        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 5)) {
            docMapperCust.increaseDocVoteCount(id);
        } else {
            throw  new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        // 推送消息
        //Doc docDB = docMapper.selectByPrimaryKey(id);
        //webSocketServer.sendInfo("【" + docDB.getName() + "】被点赞！");
        wsSocketService.sendInfo(id);
    }

    public void delete(DeleteRequest req) {
        docMapper.deleteByPrimaryKey(req.getId());
    }

    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }

    private List<Doc> getParentList(List<Doc> list) {
        List<Doc> doc = new ArrayList<>();
        for(Doc item: list) {
            if (item.getParentId() == 0) {
                doc.add(item);
            }
        }
        return doc;
    }

    private List<Doc> getChildrenList(List<Doc> list) {
        List<Doc> doc = new ArrayList<>();
        for(Doc item: list) {
            if (item.getParentId() != 0) {
                doc.add(item);
            }
        }
        return doc;
    }

    private List<DocQueryResponse> iteratorList(List<DocQueryResponse> docList, String pid) {
        List<DocQueryResponse> result = new ArrayList<>();
        for (DocQueryResponse item: docList) {
            String id = item.getId().toString();
            String subPid = item.getParentId().toString();

            if (StringUtils.isNotBlank(subPid)) {
                if (subPid.equals(pid)) {
                    List<DocQueryResponse> iteratorList = iteratorList(docList, id);
                    item.setChildren(iteratorList);
                    result.add(item);
                }
            }
        }
        return  result;
    }
}
