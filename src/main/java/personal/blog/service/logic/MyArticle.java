package personal.blog.service.logic;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import personal.blog.dto.ArticleDto;
import personal.blog.entity.Article;
import personal.blog.repository.ArticleRepository;
import personal.blog.repository.TagRepository;
import personal.blog.service.BlogService;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
public class MyArticle implements BlogService {
    private static final int ARTICLES_PER_PAGE =5;

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    @Autowired
    private ModelMapper mapper;

    public MyArticle(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void create(ArticleDto articleDto) {
        if(articleDto !=null){
            Article article = mapper.map(articleDto,Article.class);
            if(article.getTags()!=null){
                tagRepository.saveAll(article.getTags());
            }
            article.setCreationTime(LocalDateTime.now());
            article.setModificationTime(LocalDateTime.now());
            articleRepository.save(article);
        }
    }

    @Override
    public Page<ArticleDto> getAllArticles(Integer page) {
        Pageable pageable = PageRequest.of(page-1, ARTICLES_PER_PAGE, Sort.by("creationTime").descending());
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(article -> mapper.map(article, ArticleDto.class));
    }
}
