package com.blog.myblog.controller;
import com.blog.myblog.domain.Book;
import com.blog.myblog.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("/list")
    public List<Book> getBookList() {
        return bookService.list();
    }

}
