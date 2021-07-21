package personal.blog.service;

import personal.blog.entity.paging.Pagination;

public interface NewsService {
    public Pagination getNews(Integer page);

    public void getNewsFromKHTV(String url);

    public void getNewsFromTV(String url);
}
