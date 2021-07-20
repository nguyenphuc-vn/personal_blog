package personal.blog.service;

import personal.blog.entity.paging.Pagination;

public interface NewsService {
    public Pagination getNews(Integer page);

    public void getNewsFromURL(String url);
}
