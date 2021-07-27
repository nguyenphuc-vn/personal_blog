package personal.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import personal.blog.entity.News;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("select n from News n where n.href =:href")
    Optional<News> findNewsByHref(@Param("href") String href);

    @Query("select n from News n where n.website=:website")
    Page<News> findNewsByWebsite(@Param("website") String website, Pageable pageable);

    @Query("delete from News n where n.now <:datetime")
    void deleteByNowLessThan(@Param("datetime")LocalDate dateTime);
}
