package personal.blog.service;

import org.springframework.data.domain.Page;
import personal.blog.dto.ArticleDto;

import java.util.List;

public interface BlogService {
    public void create(ArticleDto articleDto);

    public Page<ArticleDto> getAllArticles(Integer page);
}
