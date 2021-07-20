package personal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import personal.blog.entity.paging.Pagination;
import personal.blog.service.NewsService;

@RestController
@RequestMapping("/api/news/")
public class NewsController {
    private static final String KHOA_HOC_URL = "https://khoahoc.tv";
    @Autowired
    private NewsService newsService;

    @ResponseBody
    @GetMapping("khoahoctv")
    public void getNewsFromKhoaHocTv() {
        newsService.getNewsFromURL(KHOA_HOC_URL);
    }

    @GetMapping
    @ResponseBody
    public Pagination getPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        return newsService.getNews(page);
    }
}
