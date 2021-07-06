package personal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.blog.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
