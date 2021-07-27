package personal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import personal.blog.dto.SingleNewsDto;
import personal.blog.entity.paging.Pagination;
import personal.blog.service.NewsService;

@RestController
@RequestMapping("/api/news/")
public class NewsController {
    private static final String KHOA_HOC_URL = "https://khoahoc.tv";
    private static final String THE_VERGE_URL = "https://www.theverge.com";
    private static final String VN_EXPRESS_URL = "https://vnexpress.net/rss/tin-moi-nhat.rss";
    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0,28 * * * *")
    @ResponseBody
    @GetMapping("khoahoctv")
    public void getNewsFromKhoaHocTv() {
        newsService.getNewsFromKHTV(KHOA_HOC_URL);
    }

    @GetMapping
    @ResponseBody
    public Pagination getPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(value = "web", required = false) String web) {
        return newsService.getNews(page, web);
    }

    @Scheduled(cron = "0,29 * * * *")
    @ResponseBody
    @GetMapping("theverge")
    public void getNewsFromTheVerge() {
        newsService.getNewsFromTV(THE_VERGE_URL);
    }

    @Scheduled(cron = "0,30 * * * *")
    @ResponseBody
    @GetMapping("vnexpress")
    public void getNewsFromVnExpress() {
        newsService.getNewsFromVE(VN_EXPRESS_URL);
    }


    @ResponseBody
    @GetMapping("article/")
    public SingleNewsDto getNewsById(@RequestParam("id") long id) {
        return newsService.getNewsById(id);
    }

    @Scheduled(cron = "0 0 */3 * *")
    @DeleteMapping("autodelete")
    public void deleteNewsOnceInThreeDay() {
        newsService.deleteByTime();
    }
}
