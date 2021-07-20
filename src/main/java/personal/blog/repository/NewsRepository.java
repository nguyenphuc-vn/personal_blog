package personal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.blog.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("select h from News h where h.href =:href")
    News findNewsByLink(String href);
}
