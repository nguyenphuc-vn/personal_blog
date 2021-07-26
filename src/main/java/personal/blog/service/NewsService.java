package personal.blog.service;

import personal.blog.dto.SingleNewsDto;
import personal.blog.entity.paging.Pagination;

public interface NewsService {
    public Pagination getNews(int page, String website);

    public void getNewsFromKHTV(String url);

    public void getNewsFromTV(String url);

    public void getNewsFromVE(String url);

    public SingleNewsDto getNewsById(long id);


}
