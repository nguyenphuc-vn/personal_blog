package personal.blog.service;

import personal.blog.dto.ArticleDto;
import personal.blog.entity.paging.Pagination;

public interface BlogService {
    public void createOrUpdate(ArticleDto articleDto);

    public Pagination getPagination(Integer page);

    public ArticleDto findArticle(Integer id);
}
