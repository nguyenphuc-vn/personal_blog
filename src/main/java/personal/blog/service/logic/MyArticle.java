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
import personal.blog.entity.paging.Pagination;
import personal.blog.repository.ArticleRepository;
import personal.blog.repository.TagRepository;
import personal.blog.service.BlogService;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class MyArticle implements BlogService {
    private static final int ARTICLES_PER_PAGE = 5;

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    @Autowired
    private ModelMapper mapper;

    public MyArticle(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void createOrUpdate(ArticleDto articleDto) {
        Article article = mapper.map(articleDto, Article.class);
        System.err.println(article.getId());
        Optional<Article> tempArticle = articleRepository.findById(article.getId());
        if (tempArticle.isEmpty()) {
            create(article);
        } else
            article = update(tempArticle, articleDto);

        articleRepository.save(article);
    }

    private void create(Article article) {
        if (article.getTags() != null) {
            tagRepository.saveAll(article.getTags());
        }
        article.setCreationTime(LocalDateTime.now());
        article.setModificationTime(LocalDateTime.now());
    }

    private Article update(Optional<Article> article, ArticleDto articleDto) {
        if (article.isPresent()) {
            article.get().setTitle(articleDto.getTitle());
            article.get().setBody(articleDto.getTitle());
            article.get().setTags(articleDto.getTags());
            article.get().setPublished(articleDto.isPublished());
            article.get().setModificationTime(LocalDateTime.now());

        }
        return article.orElse(null);
    }

    @Override
    public Pagination getPagination(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, ARTICLES_PER_PAGE, Sort.by("id").descending());
        Page<Article> articles = articleRepository.findAll(pageable);
        // call function <i,o>
        Page<ArticleDto> dtoPage = articles.map(article -> mapper.map(article, ArticleDto.class));
        return Pagination.of(dtoPage.getTotalPages(), dtoPage.getNumber(), dtoPage.getContent());
    }

    @Override
    public ArticleDto findArticle(Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        return mapper.map(article.orElse(null), ArticleDto.class);
    }


}
