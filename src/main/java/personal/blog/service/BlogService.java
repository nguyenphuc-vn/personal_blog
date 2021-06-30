package personal.blog.service.logic;

import personal.blog.dto.ArticleDto;
import personal.blog.dto.TagDto;

public interface BlogService {
    public void create(ArticleDto articleDto, TagDto tagDto);
}
