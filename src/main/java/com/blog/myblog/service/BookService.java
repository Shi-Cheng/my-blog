package com.blog.myblog.service;

import com.blog.myblog.domain.Book;
import com.blog.myblog.mapper.BookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;

    public List<Book> list() {
        return bookMapper.selectByExample(null);
    }
}
