package com.blog.myblog.service;

import com.blog.myblog.domain.Doc;
import com.blog.myblog.domain.DocExample;
import com.blog.myblog.mapper.DocMapper;
import com.blog.myblog.response.DocQueryResponse;
import com.blog.myblog.utils.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocService {

    @Resource
    private DocMapper docMapper;

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
