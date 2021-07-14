package personal.blog.service;

import personal.blog.dto.ArticleDto;
import personal.blog.entity.paging.Pagination;

public interface BlogService {
    void createOrUpdate(ArticleDto articleDto);

    Pagination getPagination(Integer page);

    ArticleDto findArticle(Integer id);

    void delete(Integer id);


}
