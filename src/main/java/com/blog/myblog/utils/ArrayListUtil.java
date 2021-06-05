package com.blog.myblog.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@Component
public class ArrayListUtil {

    public List sortDesc(List oldList) {
        int [] array = {1,3,4,3,2,5,6,3,9,22};
        TreeSet<Integer> treeSet = new TreeSet<Integer>(oldList);//转成treeset排序
        return (List) treeSet;
    }

    public List<String> duplicate(List<String> oldList) {
        List<String> list = new ArrayList<>();
        for(int i=0; i<oldList.size(); i++){
            if(!list.contains(oldList.get(i))){
                list.add(oldList.get(i));
            }
        }
        return list;
    }
}
