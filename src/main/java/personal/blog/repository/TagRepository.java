package personal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal.blog.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
